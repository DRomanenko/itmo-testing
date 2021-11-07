describe('Main', () => {
    it('Register', () => {
        const user = 'user' +  Math.random()
        cy.visit('http://localhost:3000')
        cy.contains('Register').click()
        cy.get('[name=\'login\']').type(user)
        cy.get('[name=\'password\']').type('password')
        cy.contains('Sign up').click()
        cy.get('h1').should('have.text', 'Hello, ' + user + '!These are your favourite recipes!')
    })
})