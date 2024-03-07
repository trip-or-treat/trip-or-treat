import { END_POINTS } from 'src/constants/api';

import { api } from '.';

export const placeInfoFetcher = async (id: number) => {
  const { data } = await api.get(`${END_POINTS.PLACES}/${id}/info`);
  return data;
};
