import { api } from '.';

interface Props {
  pageParam: number;
  keyword: string;
  prevContentTypeId: number | null;
  regionId: string | undefined;
}

// TODO : api요청 수정
export const placeListFetcher = async ({
  pageParam,
  keyword,
  prevContentTypeId,
  regionId,
}: Props) => {
  const { data } = await api.get(`/places?regionId=${regionId}`, {
    params: {
      keyword: keyword === '' ? null : keyword,
      contentId: prevContentTypeId,
      pageNo: pageParam,
    },
  });
  return data;
};
