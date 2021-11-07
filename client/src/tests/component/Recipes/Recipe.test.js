import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import Recipe from '../../../components/Recipes/Recipe';

describe('<Recipe/>', () => {
    it('Render Recipe', () => {
        const recipe = {
            name: 'Recipe1',
            description: 'Best recipe',
            owner: 'user'
        }
        render(<Recipe recipe={recipe}/>);
        expect(screen.getByText('Recipe1')).toBeInTheDocument()
        expect(screen.getByText('Best recipe')).toBeInTheDocument()
        expect(screen.getByText('user')).toBeInTheDocument()
    });
})