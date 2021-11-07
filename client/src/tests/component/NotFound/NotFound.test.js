import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import NotFound from '../../../components/NotFound/NotFound';

describe('<NotFound/>', () => {
    it('Render NotFound', () => {
        render(<NotFound/>);
        expect(screen.getByText('Error 404: Page does not exist!')).toBeInTheDocument()
    });
})