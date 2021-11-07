import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import RecipesService from '../../../services/RecipesService';
import App from '../../../components/App/App';

describe('<App/>', () => {
    it('Render app', async () => {
        let recipesService = new RecipesService('', 0);
        jest.spyOn(recipesService, 'currentUser').mockImplementation(async () => [null, null]);
        render(<App recipesService={recipesService}/>);
        expect(screen.getByText('Home')).toBeInTheDocument()
        expect(screen.getByText('This is Recipe list App!')).toBeInTheDocument()
    });
})