import { Navigate, Outlet } from 'react-router-dom';

const PrivateRoute = () => {
  const isLogin = !!localStorage.getItem('accessToken');

  return isLogin ? <Outlet /> : <Navigate to="/" />;
};

export default PrivateRoute;
