import { END_POINTS } from 'src/constants/api';

import { api } from '.';

export const reviewFetcher = async (placeId: number) => {
  const { data } = await api.get(`${END_POINTS.PLACES}/${placeId}/review`);
  return data;
};
