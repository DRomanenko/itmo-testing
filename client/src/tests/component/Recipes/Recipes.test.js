import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import Recipes from '../../../components/Recipes/Recipes';
import RecipesService from '../../../services/RecipesService';

describe('<Recipes/>', () => {
    it('Render Recipes', async () => {
        reporter.feature("Render")
        reporter.story("Render Recipes")
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => ['user1', null]);
        jest.spyOn(recipesService, 'getRecipes').mockImplementation(async () => [{
            recipes: [{
                name: 'Recipe1',
                description: 'Best recipe1',
                owner: 'user1'
            }, {
                name: 'Recipe2',
                description: 'Best recipe2',
                owner: 'user2'
            }],
        }, null]);
        render(<Recipes recipesService={recipesService}/>);
        await waitFor(() => {
            expect(screen.getByText('Recipe1')).toBeInTheDocument()
            expect(screen.getByText('Best recipe1')).toBeInTheDocument()
            expect(screen.getByText('user1')).toBeInTheDocument()
        })
        await waitFor(() => {
            expect(screen.getByText('Recipe2')).toBeInTheDocument()
            expect(screen.getByText('Best recipe2')).toBeInTheDocument()
            expect(screen.getByText('user2')).toBeInTheDocument()
        })
    });

    it('Render empty Recipes', async () => {
        reporter.feature("Render")
        reporter.story("Render Recipe")
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => ['user1', null]);
        jest.spyOn(recipesService, 'getRecipes').mockImplementation(async () => [{
            recipes: []
        }, null]);
        render(<Recipes recipesService={recipesService}/>);
        await waitFor(() => expect(screen.getByText('No recipes')).toBeInTheDocument())
    });
})