import { END_POINTS } from 'src/constants/api';
import { RegionsMoreInformation } from 'src/@types/api/regionsMoreInformation';
import { api } from '.';

export const regionsMoreInformationFetcher = async (id: number) => {
  const { data } = await api.get<RegionsMoreInformation[]>(`${END_POINTS.REGIONS}/${id}`);
  return data;
};
