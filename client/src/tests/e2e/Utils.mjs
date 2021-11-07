import {expect} from '@playwright/test';

export async function submit(page, login, password, buttonText = null) {
    await page.fill('input[name=\'login\']', login)
    await page.fill('input[name=\'password\']', password)
    const submitButton = await page.locator('input[type=\'submit\']')
    if (buttonText) await expect(submitButton).toHaveValue(buttonText)
    await submitButton.click()
}

export async function addRecipe(page, name, description) {
    await page.click("text=Add recipe")
    await page.fill('input[name=\'name\']', name)
    await page.fill('textarea[name=\'description\']', description)
    const addButton = await page.locator('button')
    await addButton.click()
}