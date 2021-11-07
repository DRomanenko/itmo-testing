import './Recipe.css';
import React from 'react';

export default class Recipe extends React.Component {
    render() {
        return (
            <a href='#' className='item'>
                <div className='w-100 justify-content-between d-flex'>
                    <h4>{this.props.recipe?.name}</h4>
                    <small>{this.props.recipe?.owner}</small>
                </div>
                <p>{this.props.recipe?.description}</p>
            </a>
        )
    }
}