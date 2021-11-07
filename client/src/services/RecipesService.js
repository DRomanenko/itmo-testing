export default class RecipesService {
    constructor(host, port) {
        this.server = host + ':' + port
    }

    async post(method, body) {
        return await fetch(`${this.server}/${method}`,
            {
                method: 'POST',
                credentials: 'include',
                body: JSON.stringify(body),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
    }

    async get(method) {
        return await fetch(`${this.server}/${method}`, {
            credentials: 'include'
        })
    }

    async unlogin() {
        const signOutResult = await this.post(`unlogin`)
        if (signOutResult.status !== 200) {
            return [null, (await signOutResult.json()).error]
        } else {
            return [null, null]
        }
    }

    async currentUser() {
        const currentUser = await this.get('current-user')
        if (currentUser.status !== 200) {
            return [null, await currentUser.json()]
        } else {
            const user = (await currentUser.json()).user
            if (user !== '')
                return [user, null]
            else
                return [null, null]
        }
    }

    async addRecipe(name, description) {
        const result = await this.post(
            `add-recipe`,
            {
                name: name,
                description: description
            }
        )
        if (result.status !== 200) {
            return [null, (await result.json()).error]
        } else {
            return [null, null]
        }
    }

    async getRecipes(user) {
        const result = await this.get(`recipes` + (user != null ? ('?user=' + user) : ''))
        if (result.status !== 200) {
            return [null, (await result.json()).error]
        } else {
            return [await result.json(), null]
        }
    }
}