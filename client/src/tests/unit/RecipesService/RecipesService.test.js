import RecipesService from '../../../services/RecipesService';
import {waitFor} from '@testing-library/react';

describe('<RecipesService/>', () => {
    it('currentUser', async () => {
        let recipesService = new RecipesService('', 0);
        global.fetch = jest.fn(async (args) => {
                if (args.includes('/current-user')) {
                    return {
                        json: async () => ({user: 'user'}),
                        status: 200
                    }
                }
            }
        );
        await waitFor(async () => expect((await recipesService.currentUser())[0]).toBe('user'))
    });

    it('getRecipes', async () => {
        let recipesService = new RecipesService('', 0);
        global.fetch = jest.fn(async (args) => {
                if (args.includes('/recipes')) {
                    return {
                        json: async () => ({recipes: 'recipes'}),
                        status: 200
                    }
                }
            }
        );
        await waitFor(async () => expect((await recipesService.getRecipes())[0]).toStrictEqual({recipes: 'recipes'}))
    });
})