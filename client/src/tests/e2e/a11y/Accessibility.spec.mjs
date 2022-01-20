import {test} from '@playwright/test';
import {testA11y} from "../Steps.mjs";

test.describe("A11y", () => {
    test('Home accessibility', async ({page}) => {
        await testA11y(page, '/')
    })

    test('Login accessibility', async ({page}) => {
        await testA11y(page, '/login')
    })

    test('Register accessibility', async ({page}) => {
        await testA11y(page, '/register')
    })
})