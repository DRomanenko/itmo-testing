import './Register.css';
import React from 'react';
import Base from '../Base/Base';

export default class Register extends Base {
    constructor(props) {
        super(props);
        this.state = {
            login: '',
            password: ''
        }
        this.changeLogin = this.changeLogin.bind(this)
        this.changePassword = this.changePassword.bind(this)
    }

    changeLogin(event) {
        this.setState({
            login: event.target.value
        });
    }

    changePassword(event) {
        this.setState({
            password: event.target.value
        });
    }

    render() {
        return (
            <div className='register-form'>
                <form method='post' name='register' autoComplete='off'
                      action={process.env.REACT_APP_SERVER_HOST + ':' + process.env.REACT_APP_SERVER_PORT + '/register'}>
                    <div className='form-text'>
                        <div className={'label'}>Login</div>
                        <input type='text' id='login' name='login' className='form-control' placeholder='Login'/>
                    </div>
                    <div className='form-text'>
                        <div className={'label'}>Password</div>
                        <input type='password' id='password' name='password' className='form-control' placeholder='Password'/>
                    </div>

                    <input type='submit' className={'login-button'} value='Sign up'/>
                    <input type='hidden' name='redirectTo'
                           value={window.location.protocol + '//' + window.location.host + '/'}/>
                </form>
            </div>
        )
    }
}