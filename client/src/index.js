import React from 'react';
import {config} from 'dotenv'
import ReactDOM from 'react-dom';
import App from './components/App/App';
import 'bootstrap/dist/css/bootstrap.min.css';
import RecipesService from './services/RecipesService';

config()

ReactDOM.render(
    <React.StrictMode>
        <App recipesService={new RecipesService(process.env.REACT_APP_SERVER_HOST, process.env.REACT_APP_SERVER_PORT)}/>
    </React.StrictMode>,
    document.getElementById('root')
);
