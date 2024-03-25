import { useKakaoLogin } from 'src/hooks/api/useKakaoLogin';
import { Navigate } from 'react-router-dom';
import Loading from '../common/Loading';

const KaKaoLogin = () => {
  const code = new URL(window.location.href).searchParams.get('code');
  const { data: serverToken, isLoading, isError } = useKakaoLogin(code);
  const prevPage = localStorage.getItem('prevPage');

  if (serverToken) localStorage.setItem('accessToken', serverToken.split('Bearer ')[1]);

  if (isError) {
    alert('로그인에 실패했습니다.');
    window.location.href = '/';
  }

  return isLoading ? (
    <Loading type="LARGE" />
  ) : (
    (prevPage && <Navigate to={prevPage} />) || <Navigate to="/" />
  );
};

export default KaKaoLogin;
