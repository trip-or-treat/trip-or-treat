import StepLayout from './layouts/StepLayout';
import StepOnePage from './pages/StepOnePage';

const routes = [
  {
    element: <StepLayout />,
    children: [{ path: '/date/:region_id', element: <StepOnePage /> }],
  },
];
export default routes;
