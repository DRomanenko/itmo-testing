import React from 'react';
import Base from '../Base/Base';
import Recipe from './Recipe';

export default class Recipes extends Base {
    constructor(props) {
        super(props);

        this.state = {
            recipes: []
        }
    }

    componentDidMount() {
        this.recipesService.currentUser().then(u => {
            this.setState({user: u[0]})
            this.recipesService.getRecipes(u[0]).then(pl => {
                this.setState({
                    recipes: pl[0].recipes ?? []
                })
            })
        })
    }

    render() {
        return (
            <center>
                <div className='list-group m-lg-3 w-50' style={{ color: '#f8f8f2' }}>
                    {this.state.recipes.length === 0
                        ? 'No recipes'
                        : this.state.recipes.map(l => {
                            return (<Recipe key={l.name + '-' + l.owner} recipe={l}/>)
                        })}
                </div>
            </center>
        )
    }
}