// noinspection JSCheckFunctionSignatures

const express = require('express')
const cookieParser = require('cookie-parser')
const session = require('express-session');
const bodyParser = require('body-parser');
const cors = require('cors')
const {RecipesDatabase} = require("./database");
const app = express()
const sjcl = require('sjcl')

function start(port, dbString, sessionSecret, allowedHosts) {
    const recipesDB = new RecipesDatabase(dbString)

    app.use(bodyParser.json());
    app.use(express.urlencoded());
    app.use(cookieParser());
    app.use(session({
        secret: sessionSecret,
        name: 'session',
        cookie: {
            expires: new Date(Date.now() + 60 * 60 * 1000),
            sameSite: true
        }
    }));
    app.options('http://localhost:' + port);
    app.use(function (req, res, next) {
        res.setHeader('Access-Control-Allow-Origin', allowedHosts);
        res.setHeader('Access-Control-Allow-Credentials', 'true');
        res.setHeader('Access-Control-Allow-Methods', 'GET,POST');
        res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
        res.setHeader('Cache-Control', 'no-store');
        next();
    });

    app.post('/register', (req, res) => {
        const passwordBitArray = sjcl.hash.sha256.hash(req.body.password)
        const passwordHash = sjcl.codec.hex.fromBits(passwordBitArray)
        recipesDB.createUser({
            login: req.body.login,
            password: passwordHash
        }, rows => {
            req.session.user = req.body.login
            res.redirect(301, req.body.redirectTo)
        }, err => {
            res.redirect(301, req.body.redirectTo + 'register')
        });
    })

    app.post('/login', (req, res) => {
        const passwordBitArray = sjcl.hash.sha256.hash(req.body.password)
        const passwordHash = sjcl.codec.hex.fromBits(passwordBitArray)
        recipesDB.getUserByLoginAndPassword({
            login: req.body.login,
            password: passwordHash
        }, rows => {
            if (rows.length !== 1) {
                res.redirect(301, req.body.redirectTo + 'login')
            } else {
                req.session.user = rows[0].login
                res.redirect(301, req.body.redirectTo)
            }
        }, err => {
            res.redirect(301, req.body.redirectTo + 'login')
        });
    })

    app.post('/unlogin', (req, res) => {
        res.status(200)
        req.session.user = null
        res.end()
    })

    app.get('/current-user', (req, res) => {
        res.status(200)
        res.send(JSON.stringify({user: req.session.user}))
    })

    app.post('/add-recipe',
        (req, res) => {
            if (req.session.user) {
                recipesDB.getUserByLogin(req.session.user, u => {
                    recipesDB.addRecipe(req.body, u[0].userId,
                        _ => {
                            res.status(200)
                            res.end()
                        }, error => {
                            res.status(400)
                            res.send(JSON.stringify({error: "Can't create recipe"}))
                        })
                }, error => {
                    res.status(400)
                    res.send(JSON.stringify({error: "Can't find user"}))
                })
            } else {
                res.status(401)
                res.send(JSON.stringify({error: "Login to create recipes"}))
            }
        }
    )

    app.get('/recipes', (req, res) => {
        recipesDB.getRecipes(req.query.user,
            lists => {
                recipesDB.getAddedRecipes(req.query.user ?? -1, added => {
                    res.status(200)
                    res.contentType("application/json")
                    res.send(
                        JSON.stringify({
                            recipes: lists.map((row) => {
                                return {
                                    id: row.recipeId,
                                    name: row.name,
                                    description: row.description,
                                    owner: row.login
                                };
                            }),
                            addedRecipes: added.map((row) => {
                                return {
                                    id: row.recipeId,
                                    name: row.name,
                                    description: row.description,
                                    owner: row.login
                                };
                            })
                        })
                    );
                }, err => {
                    res.status(500)
                    res.send(JSON.stringify({error: err.message}))
                })
            }, err => {
                res.status(500)
                res.send(JSON.stringify({error: err.message}))
            });
    })

    app.listen(port, () => {
        console.log(`App listening at http://localhost:${port}`)
    })
}

exports.start = start