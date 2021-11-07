import './AddRecipe.css';
import React from 'react';
import Base from '../Base/Base';
import Greeting from '../Greeting/Greeting';

export default class AddRecipe extends Base {
    constructor(props) {
        super(props);

        this.state = {
            name: '',
            description: '',
            error: null
        }

        this.submitAdd = this.submitAdd.bind(this)
        this.changeName = this.changeName.bind(this)
        this.changeDescription = this.changeDescription.bind(this)
    }

    componentDidMount() {
        super.reloadUser()
    }

    async submitAdd() {
        let [_, err] = await this.recipesService.addRecipe(this.state.name, this.state.description)
        if (err) {
            this.setState({
                error: err
            })
        } else {
            window.location.href = '/'
        }
    }

    changeName(event) {
        this.setState({
            name: event.target.value
        });
    }

    changeDescription(event) {
        this.setState({
            description: event.target.value
        });
    }

    render() {
        if (this.state.user) {
            return (
                <div className='add-recipe'>
                    <h3>Add Recipe</h3>

                    <div>{this.state.error}</div>

                    <div className='form-text'>
                        <div className={'label'}>Name</div>
                        <input type='text' name='name' className='form-control' placeholder='Name'
                               onChange={this.changeName}/>
                    </div>
                    <div className='form-text'>
                        <div className={'label'}>Description</div>
                        <textarea name='description' className='form-control' placeholder='Description'
                                  onChange={this.changeDescription}/>
                    </div>

                    <button className={'login-button'} onClick={this.submitAdd}>Add</button>
                </div>
            )
        } else {
            return (
                <Greeting recipesService={this.recipesService}/>
            )
        }
    }
}