import RecipesService from '../../../services/RecipesService';
import {waitFor} from '@testing-library/react';

describe('<RecipesService/>', () => {
    function allureInfo(story) {
        reporter.epic("Client: Unit Tests")
        reporter.feature("Recipes Service")
        reporter.story(story)
    }

    it('currentUser', async () => {
        allureInfo("Get current user")

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
        allureInfo("Get current recipes")

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