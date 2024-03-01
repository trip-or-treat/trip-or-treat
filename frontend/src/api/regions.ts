import { END_POINTS } from 'src/constants/api';
import { Regions } from 'src/@types/api/regions';
import { api } from '.';

export const regionsFetcher = async () => {
  const { data } = await api.get<Regions[]>(END_POINTS.REGIONS);
  return data;
};
