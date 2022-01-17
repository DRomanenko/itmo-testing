import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import RecipesService from '../../../services/RecipesService';
import Main from '../../../components/Main/Main';

describe('<Main/>', () => {
    it('Render Main for an unauthorised user', () => {
        reporter.feature("Render")
        reporter.story("Render Main page")
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => [null, null]);
        render(<Main recipesService={recipesService}/>);
        expect(screen.getByText('This is Recipe list App!')).toBeInTheDocument()
        expect(screen.getByText('Once logged in, you can add your favourite recipes!')).toBeInTheDocument()
    });

    it('Render Main for an authorised user', async () => {
        reporter.feature("Render")
        reporter.story("Render Main page")
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => ['user', null]);
        render(<Main recipesService={recipesService}/>);
        await waitFor(() => {
            expect(screen.getByText('Hello, user!')).toBeInTheDocument()
            expect(screen.getByText('These are your favourite recipes!')).toBeInTheDocument()
        })
    });
})