import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import RecipesService from '../../../services/RecipesService';
import Login from '../../../components/Login/Login';

describe('<Login/>', () => {
    it('Render Login', () => {
        reporter.feature("Render")
        reporter.story("Render Login page")
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => [null, null]);
        render(<Login recipesService={recipesService}/>);
        expect(screen.getByText('Login')).toBeInTheDocument()
        expect(screen.getByText('Password')).toBeInTheDocument()
        expect(screen.getByText('Sign in')).toBeInTheDocument()
    });
})