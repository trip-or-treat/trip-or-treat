import { Outlet } from 'react-router-dom';
import Nav from 'src/components/common/Nav';

const CommonLayout = () => {
  return (
    <>
      <Nav />
      <Outlet />
    </>
  );
};

export default CommonLayout;
