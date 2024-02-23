import StepLayout from './layouts/StepLayout';
import StepOnePage from './pages/StepOnePage';
import StepThreePage from './pages/StepThreePage';
import StepTwoPage from './pages/StepTwoPage';

const routes = [
  {
    element: <StepLayout />,
    children: [
      { path: '/date/:region_id', element: <StepOnePage /> },
      { path: '/region/:region_id', element: <StepTwoPage /> },
      { path: '/place/:region_id', element: <StepThreePage /> },
    ],
  },
];
export default routes;
