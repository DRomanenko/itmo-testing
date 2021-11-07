import './Main.css';
import React from 'react';
import Base from '../Base/Base';
import Greeting from '../Greeting/Greeting';
import Recipes from '../Recipes/Recipes';

export default class Main extends Base {
    componentDidMount() {
        super.reloadUser()
    }

    render() {
        if (this.state.user) {
            return (
                <div>
                    <Greeting recipesService={this.recipesService}/>
                    <Recipes recipesService={this.recipesService}/>
                </div>
            )
        } else {
            return (
                <Greeting recipesService={this.recipesService}/>
            )
        }
    }
}