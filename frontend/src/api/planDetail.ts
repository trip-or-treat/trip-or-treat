import { END_POINTS } from 'src/constants/api';
import { loginApi } from '.';

export const planDetailFetcher = async (id: string | undefined) => {
  const { data } = await loginApi.get(`${END_POINTS.PLAN}/${id}`);
  return data;
};
