import { Outlet } from 'react-router-dom';

import CommonNav from 'src/components/common/CommonNav/CommonNav';

const CommonLayout = () => {
  return (
    <>
      <CommonNav />
      <Outlet />
    </>
  );
};

export default CommonLayout;
