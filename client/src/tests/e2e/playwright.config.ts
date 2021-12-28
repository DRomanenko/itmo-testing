import {PlaywrightTestConfig} from "@playwright/test";

require('dotenv').config({ path: './.env.test' });

const getBaseUrl = () => {
    return process.env.E2E_TESTS_URL ?? 'http://localhost:3000'
}

const config: PlaywrightTestConfig = {
    use: {
        baseURL: getBaseUrl(),
        headless: true,
        viewport: { width: 1280, height: 720 },
        ignoreHTTPSErrors: true,
    },
}

export default config;