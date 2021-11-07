import './Login.css';
import React from 'react';
import Base from '../Base/Base';

export default class Login extends Base {
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
            <div className='login-form'>
                <form method='post' name='login' autoComplete='off'
                      action={process.env.REACT_APP_SERVER_HOST + ':' + process.env.REACT_APP_SERVER_PORT + '/login'}>

                    <div className='form-text'>
                        <div className={'label'}>Login</div>
                        <input type='text' name='login' className='form-control' placeholder='Login'/>
                    </div>

                    <div className='form-text'>
                        <div className={'label'}>Password</div>
                        <input type='password' name='password' className='form-control' placeholder='Password'/>
                    </div>

                    <input type='submit' className={'login-button'} value='Sign in'/>
                    <input type='hidden' name='redirectTo'
                           value={window.location.protocol + '//' + window.location.host + '/'}/>
                </form>
            </div>
        )
    }
}