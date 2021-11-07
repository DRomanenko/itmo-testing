import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import RecipesService from '../../../services/RecipesService';
import Greeting from '../../../components/Greeting/Greeting';

describe('<Greeting/>', () => {
    it('Render Greeting for an unauthorised user', () => {
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => [null, null]);
        render(<Greeting recipesService={recipesService}/>);
        expect(screen.getByText('This is Recipe list App!')).toBeInTheDocument()
        expect(screen.getByText('Once logged in, you can add your favourite recipes!')).toBeInTheDocument()
    });

    it('Render Greeting for an authorised user', async () => {
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => ['user', null]);
        render(<Greeting recipesService={recipesService}/>);
        await waitFor(() => {
            expect(screen.getByText('Hello, user!')).toBeInTheDocument()
            expect(screen.getByText('These are your favourite recipes!')).toBeInTheDocument()
        })
    });
})