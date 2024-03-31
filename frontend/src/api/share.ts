import { END_POINTS } from 'src/constants/api';

import { api } from '.';

export const shareFetcher = async (code: string) => {
  const { data } = await api.get(`${END_POINTS.SHARE}/${code}`);
  return data;
};
