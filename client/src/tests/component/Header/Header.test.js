import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import Header from '../../../components/Header/Header';
import RecipesService from '../../../services/RecipesService';

describe('<Header/>', () => {
    it('Render Header for an unauthorised user', () => {
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => [null, null]);
        render(<Header recipesService={recipesService}/>);
        expect(screen.getByText('Home')).toBeInTheDocument()
        expect(screen.getByText('Login')).toBeInTheDocument()
        expect(screen.getByText('Register')).toBeInTheDocument()
    });

    it('Render Header for an authorised user', async () => {
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => ['user', '123']);
        render(<Header recipesService={recipesService}/>);
        expect(screen.getByText('Home')).toBeInTheDocument()
        await waitFor(() => {
            expect(screen.getByText('Add recipe')).toBeInTheDocument()
            expect(screen.getByText('Sign out')).toBeInTheDocument()
        })
    });
})