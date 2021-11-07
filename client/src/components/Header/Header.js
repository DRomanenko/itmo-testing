import './Header.css';
import React from 'react';
import Base from '../Base/Base';

export default class Header extends Base {
    constructor(props) {
        super(props);
        this.unlogin = this.unlogin.bind(this)
    }

    componentDidMount() {
        super.reloadUser()
    }

    async unlogin() {
        await this.recipesService.unlogin()
        super.reloadUser()
        window.location.href = '/'
    }

    render() {
        if (this.state.user) {
            return (
                <div className='header'>
                    <ul>
                        <li className='left'>
                            <a href='/'>Home</a>
                        </li>
                        <li className='left'>
                            <div>|</div>
                        </li>
                        <li className='left'>
                            <a href='/add-recipe'>Add recipe</a>
                        </li>

                        <li className='right'>
                            <a href='#' onClick={this.unlogin}>Sign out</a>
                        </li>
                    </ul>
                </div>
            )
        } else {
            return (
                <div className='header'>
                    <ul>
                        <li className='left'>
                            <a href='/'>Home</a>
                        </li>

                        <li className='right'>
                            <a href='/register'>Register</a>
                        </li>
                        <li className='right'>
                            <a href='/login'>Login</a>
                        </li>
                    </ul>
                </div>
            )
        }
    }
}