const server = require('./src/server')
require('dotenv').config({path: __dirname + '/.env'})
server.start(process.env.SERVER_PORT, process.env.DATABASE, process.env.SESSION_SECRET, process.env.ALLOWED_HOSTS)