import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import AddRecipe from '../../../components/Recipes/AddRecipe';
import RecipesService from '../../../services/RecipesService';

describe('<AddRecipe/>', () => {
    it('Render AddRecipe for an unauthorised user', () => {
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => [null, null]);
        render(<AddRecipe recipesService={recipesService}/>);
        expect(screen.getByText('This is Recipe list App!')).toBeInTheDocument()
        expect(() => screen.getByText('Add recipe')).toThrow()
        expect(() => screen.getByText('Name')).toThrow()
        expect(() => screen.getByText('Description')).toThrow()
        expect(() => screen.getByText('Add')).toThrow()
    });
    
    it('Render AddRecipe for an authorised user', async () => {
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => ['user', '123']);
        render(<AddRecipe recipesService={recipesService}/>);
        await waitFor(() => {
            expect(screen.getByText('Name')).toBeInTheDocument()
            expect(screen.getByText('Description')).toBeInTheDocument()
            expect(screen.getByText('Add')).toBeInTheDocument()
        })
    });
})