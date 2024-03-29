export const useLogin = () => {
  const token = localStorage.getItem('accessToken');
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const tokenRenewal = (authorization: string) => {
    localStorage.setItem('accessToken', authorization.split('Bearer ')[1]);
  };

  const loginError = () => {
    alert('로그인 유효시간이 만료되었습니다.');
    localStorage.removeItem('accessToken');
    window.location.href = '/';
  };

  return { headers, tokenRenewal, loginError };
};
