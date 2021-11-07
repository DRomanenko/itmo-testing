import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import RecipesService from '../../../services/RecipesService';
import Register from '../../../components/Register/Register';

describe('<Register/>', () => {
    it('Render Register', () => {
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => [null, null]);
        render(<Register recipesService={recipesService}/>);
        expect(screen.getByText('Login')).toBeInTheDocument()
        expect(screen.getByText('Password')).toBeInTheDocument()
        expect(screen.getByText('Sign up')).toBeInTheDocument()
    });
})