import CommonLayout from './layouts/CommonLayout';
import MainPage from './pages/MainPage';

import StepLayout from './layouts/StepLayout';
import StepOnePage from './pages/StepOnePage';
import StepThreePage from './pages/StepThreePage';
import StepTwoPage from './pages/StepTwoPage';

const routes = [
  {
    element: <CommonLayout />,
    children: [{ path: '/', element: <MainPage /> }],
  },

  {
    element: <StepLayout />,
    children: [
      { path: '/date/:regionId', element: <StepOnePage /> },
      { path: '/region/:regionId', element: <StepTwoPage /> },
      { path: '/place/:regionId', element: <StepThreePage /> },
    ],
  },
];
export default routes;
