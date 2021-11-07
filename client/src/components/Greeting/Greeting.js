import './Greeting.css';
import React from 'react';
import Base from '../Base/Base';

export default class Greeting extends Base {
    componentDidMount() {
        super.reloadUser()
    }

    render() {
        if (this.state.user) {
            return (
                <div className='greeting-logged'>
                    <h1>
                        <span>Hello, {this.state.user}!</span><br/>
                        <span>These are your favourite recipes!</span>
                    </h1>
                </div>
            )
        } else {
            return (
                <div className='greeting-unlogged'>
                    <h1>This is Recipe list App!</h1>
                    <p>Once logged in, you can add your favourite recipes!</p>
                </div>
            )
        }
    }
}