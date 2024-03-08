import { END_POINTS } from 'src/constants/api';
import { api } from '.';

export const contentTypeFetcher = async () => {
  const { data } = await api.get(END_POINTS.CONTENT_TYPE);
  return data;
};
