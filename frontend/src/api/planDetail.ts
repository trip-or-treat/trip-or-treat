import { END_POINTS } from 'src/constants/api';
import { api } from '.';

export const planDetailFetcher = async (id: string | undefined) => {
  const token = localStorage.getItem('accessToken');
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const { data } = await api.get(`${END_POINTS.PLAN}/${id}`, { headers });
  return data;
};
