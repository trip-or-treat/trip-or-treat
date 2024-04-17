import { loginApi } from 'src/api';
import { END_POINTS } from 'src/constants/api';

export const useModifyNickname = async ({ nickname }: { nickname: string }) => {
  const token = localStorage.getItem('accessToken');
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await loginApi.put(END_POINTS.NICKNAME, { nickname }, { headers });
  return response;
};
