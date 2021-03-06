import {test, expect} from '@playwright/test';
import {submitCredentials} from "./Steps.mjs";

test.describe('Register', () => {
    test('Register, Logout, Login, check session', async ({page}) => {
        const random = Math.random()

        const login = 'user' + random
        const password = 'password' + random

        await page.goto('http://localhost:3000/register')
        await submitCredentials(page, login, password, 'Sign up')

        await expect(page.locator('h1'))
            .toHaveText('Hello, user' + random + '!These are your favourite recipes!')

        await page.locator('text=Sign out').click()
        await expect(await page.locator('h1'))
            .toHaveText('This is Recipe list App!')

        await page.click('text=Login')
        await submitCredentials(page, login, password, 'Sign in')
        await expect(await page.locator('h1'))
            .toHaveText('Hello, user' + random + '!These are your favourite recipes!')
    })
})