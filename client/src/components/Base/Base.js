import React from 'react';

export default class Base extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null
        }
        this.recipesService = props.recipesService
        this.reloadUser = this.reloadUser.bind(this)
    }

    reloadUser() {
        this.recipesService.currentUser().then(res => {
            this.setState({user: res[0]})
        })
    }
}