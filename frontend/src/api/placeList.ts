import { api } from '.';

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
  const token = localStorage.getItem('accessToken');
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const { data } = await api.get(`/places?regionId=${regionId}`, {
    headers,
    params: {
      keyword: keyword === '' ? null : keyword,
      contentTypeId: prevContentTypeId,
      page: pageParam,
    },
  });

  return data;
};
