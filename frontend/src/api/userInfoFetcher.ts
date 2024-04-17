import { END_POINTS } from 'src/constants/api';
import { loginApi } from '.';

export const userInfoFetcher = async () => {
  const { data } = await loginApi.get(END_POINTS.INFO);
  return data;
};
