import { useEffect } from 'react';

export const useCheckUser = () => {
  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (!token) {
      alert('로그인이 필요한 서비스입니다.');
      window.location.href = '/';
    }
  }, []);
};
