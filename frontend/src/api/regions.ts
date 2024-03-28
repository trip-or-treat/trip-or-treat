import { END_POINTS } from 'src/constants/api';

import { api } from '.';

export const regionsFetcher = async () => {
  const { data } = await api.get(END_POINTS.REGIONS);
  return data;
};
