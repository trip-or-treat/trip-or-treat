import CommonLayout from './layouts/CommonLayout';
import MainPage from './pages/MainPage';

import StepLayout from './layouts/StepLayout';
import StepOnePage from './pages/StepOnePage';
import StepThreePage from './pages/StepThreePage';
import StepTwoPage from './pages/StepTwoPage';

import NotFoundPage from './pages/NotFoundPage';
import PlansPage from './pages/PlansPage';

import MyPageLayout from './layouts/MyPageLayout';
import MyInfoPage from './pages/MyInfoPage';
import MyPlanPage from './pages/MyPlanPage';
import MyReviewPage from './pages/MyReviewPage';

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
    element: <MyPageLayout />,
    children: [
      { path: '/mypage/myInfo', element: <MyInfoPage /> },
      { path: '/mypage/myPlan', element: <MyPlanPage /> },
      { path: '/mypage/myReview', element: <MyReviewPage /> },
    ],
  },
];

export default routes;
