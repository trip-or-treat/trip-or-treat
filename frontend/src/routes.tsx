import CommonLayout from './layouts/CommonLayout';
import MainPage from './pages/MainPage';

import StepLayout from './layouts/StepLayout';
import StepOnePage from './pages/StepOnePage';
import StepThreePage from './pages/StepThreePage';
import StepTwoPage from './pages/StepTwoPage';

import NotFoundPage from './pages/NotFoundPage';
import PlansPage from './pages/PlansPage';
import KaKaoLogin from './components/KaKaoLogin';

const routes = [
  {
    element: <CommonLayout />,
    children: [
      { path: '/', element: <MainPage /> },
      { path: '/plans', element: <PlansPage /> },
    ],
  },

  {
    element: <StepLayout />,
    children: [
      { path: '/date/:regionId', element: <StepOnePage /> },
      { path: '/region/:regionId', element: <StepTwoPage /> },
      { path: '/place/:regionId', element: <StepThreePage /> },
    ],
  },

  {
    element: <CommonLayout />,
    children: [{ path: '/*', element: <NotFoundPage /> }],
  },
  {
    path: '/login',
    element: <KaKaoLogin />,
  },
];

export default routes;
