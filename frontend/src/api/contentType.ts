import { END_POINTS } from 'src/constants/api';
import { ContentType } from 'src/@types/api/contentType';
import { api } from '.';

export const contentTypeFetcher = async () => {
  const { data } = await api.get<ContentType[]>(`${END_POINTS.CONTENT_TYPE}`);
  return data;
};
