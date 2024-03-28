import { END_POINTS } from 'src/constants/api';

import { api } from '.';

export const regionsMoreInformationFetcher = async (id: number) => {
  const { data } = await api.get(`${END_POINTS.REGIONS}/${id}`);
  return data;
};
