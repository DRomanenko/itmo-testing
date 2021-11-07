import {test, expect} from '@playwright/test';
import {addRecipe, submit} from "./Utils.mjs";

test.describe('Recipes', () => {
    test('Add Recipe', async ({page}) => {
        const random = Math.random()
        const login = 'login' + random
        const password = 'password' + random
        await page.goto('http://localhost:3000/register')
        await submit(page, login, password)
        const name = 'name'
        const description = 'description'
        await addRecipe(page, name + random, description + random)
        await expect(await page.locator('text=' + name + random)).toHaveText(name + random)
        await expect(await page.locator('text=' + description + random)).toHaveText(description + random)
    })
})