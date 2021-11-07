const sqlite = require('sqlite3')

class RecipesDatabase {
    constructor(db_path) {
        this.db = new sqlite.Database(db_path)
        this.query(
            'CREATE TABLE IF NOT EXISTS Users (' +
            '   userId INTEGER PRIMARY KEY AUTOINCREMENT,' +
            '   login VARCHAR(64) UNIQUE,' +
            '   password VARCHAR(64)' +
            ')'
        )
        this.query(
            'CREATE TABLE IF NOT EXISTS Recipes (' +
            '   recipeId INTEGER PRIMARY KEY AUTOINCREMENT,' +
            '   name VARCHAR(64),' +
            '   description VARCHAR(1024),' +
            '   ownerId INTEGER,' +
            '   FOREIGN KEY (ownerId) REFERENCES Users(userId),' +
            '   CONSTRAINT recipe_owner UNIQUE(name,ownerId)' +
            ')'
        )
        this.query(
            'CREATE TABLE IF NOT EXISTS RecipesToUsers (' +
            '   recipeId INTEGER,' +
            '   userId INTEGER,' +
            '   PRIMARY KEY (recipeId, userId),' +
            '   FOREIGN KEY (userId) REFERENCES Users(userId),' +
            '   FOREIGN KEY (recipeId) REFERENCES Recipes(recipeId)' +
            ')'
        )
    }

    getUserByLogin(login, onSuccess, onError) {
        this.query(
            `SELECT *
             FROM Users
             WHERE login = \'${login}\'`,
            onSuccess, onError
        )
    }

    getUserByLoginAndPassword(user, onSuccess, onError) {
        this.query(
            `SELECT *
             FROM Users
             WHERE login = \'${user.login}\'
               AND password = \'${user.password}\'`,
            onSuccess, onError
        )
    }

    createUser(user, onSuccess, onError) {
        this.query(
            `INSERT INTO Users (login, password)
             VALUES ('${user.login}', '${user.password}')`,
            onSuccess, onError
        )
    }

    addRecipe(recipe, ownerId, onSuccess, onError) {
        this.query(
            `INSERT INTO Recipes (name, description, ownerId)
             VALUES (\'${recipe.name}\', \'${recipe.description}\', ${ownerId})`,
            onSuccess, onError
        )
    }

    getRecipes(user, onSuccess, onError) {
        let query = `SELECT *
                     FROM Recipes AS l
                              JOIN Users AS u ON l.ownerId = u.userId`
        if (user) {
            query += ` AND u.login = \'${user}\'`
        }
        this.query(query, onSuccess, onError)
    }

    getAddedRecipes(user, onSuccess, onError) {
        this.query(
            `SELECT l.recipeId, l.name, l.description, o.login
             FROM Recipes AS l
                      NATURAL JOIN RecipesToUsers AS lt
                      JOIN Users AS o ON l.ownerId = o.userId
                      JOIN Users AS u ON lt.userId = u.userId
             WHERE u.login = \'${user}\'`,
            onSuccess, onError
        )
    }

    query(queryString, onSuccess, onError) {
        this.db.all(queryString, [], (e, r) => {
            if (r && onSuccess) onSuccess(r)
            else if (e) {
                console.log(e)
                if (onError) {
                    onError(e)
                }
            }
        })
    }
}

exports.RecipesDatabase = RecipesDatabase