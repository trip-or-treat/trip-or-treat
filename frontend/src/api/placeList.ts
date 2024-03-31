import { END_POINTS } from 'src/constants/api';
import { loginApi } from '.';

interface Props {
  pageParam: number;
  keyword: string;
  prevContentTypeId: number | null;
  regionId: number;
}

export const placeListFetcher = async ({
  pageParam,
  keyword,
  prevContentTypeId,
  regionId = 0,
}: Props) => {
  const { data } = await loginApi.get(`${END_POINTS.PLACES}?regionId=${regionId}`, {
    params: {
      keyword: keyword === '' ? null : keyword,
      contentTypeId: prevContentTypeId,
      page: pageParam,
    },
  });

  return data;
};
