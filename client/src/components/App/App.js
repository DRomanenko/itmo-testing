import React from 'react';
import {QueryClient, QueryClientProvider} from 'react-query';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Main from '../Main/Main';
import Header from '../Header/Header';
import Base from '../Base/Base';
import Login from '../Login/Login';
import Register from '../Register/Register';
import AddRecipe from '../Recipes/AddRecipe';
import Recipes from '../Recipes/Recipes';
import NotFound from '../NotFound/NotFound';


export default class App extends Base {
    render() {
        return (
            <div style={{ font: "JetBrains Mono", background: '#44475a' }}>
                <QueryClientProvider client={new QueryClient()}>
                    <BrowserRouter>
                        <Header recipesService={this.recipesService}/>
                        <Switch>
                            <Route exact path='/'>
                                <Main recipesService={this.recipesService}/>
                            </Route>
                            <Route path='/add-recipe'>
                                <AddRecipe recipesService={this.recipesService}/>
                            </Route>
                            <Route path='/recipes'>
                                <Recipes recipesService={this.recipesService}/>
                            </Route>
                            <Route path='/login'>
                                <Login recipesService={this.recipesService}/>
                            </Route>
                            <Route path='/register'>
                                <Register recipesService={this.recipesService}/>
                            </Route>
                            <Route component={NotFound}/>
                        </Switch>
                    </BrowserRouter>
                </QueryClientProvider>
            </div>
        )
    }
}