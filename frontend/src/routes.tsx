import CommonLayout from './layouts/CommonLayout';
import MyPageLayout from './layouts/MyPageLayout';
import StepLayout from './layouts/StepLayout';

import MainPage from './pages/MainPage';

import NotFoundPage from './pages/NotFoundPage';

import StepOnePage from './pages/StepOnePage';
import StepThreePage from './pages/StepThreePage';
import StepTwoPage from './pages/StepTwoPage';

import PlansPage from './pages/PlansPage';
import PlansChangePage from './pages/PlansChangePage';

import MyInfoPage from './pages/MyInfoPage';
import MyPlanPage from './pages/MyPlanPage';
import MyReviewPage from './pages/MyReviewPage';

import KaKaoLogin from './components/KaKaoLogin';

const routes = [
  {
    element: <CommonLayout />,
    children: [
      { path: '/', element: <MainPage /> },
      { path: '/plans', element: <PlansPage /> },
      { path: '/mypage/plans/:planId', element: <PlansChangePage /> },
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
    element: <MyPageLayout />,
    children: [
      { path: '/mypage/myInfo', element: <MyInfoPage /> },
      { path: '/mypage/myPlan', element: <MyPlanPage /> },
      { path: '/mypage/myReview', element: <MyReviewPage /> },
    ],
  },

  {
    path: '/login',
    element: <KaKaoLogin />,
  },
];

export default routes;
