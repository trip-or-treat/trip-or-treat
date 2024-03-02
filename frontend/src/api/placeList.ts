import axios from 'axios';
// import { api } from '.';

interface Props {
  pageParam: number;
  keyword: string;
  prevContentTypeId: number | null;
  regionId: string | undefined;
}

// export const placeListFetcher = async ({
//   pageParam,
//   keyword,
//   prevContentTypeId,
//   regionId,
// }: Props) => {
//   const { data } = await api.get(
//     `https://6edb4ad1-bd38-4f7b-b4ff-b64db80e4610.mock.pstmn.io/places?regionId=${regionId}`,
//     {
//       params: {
//         keyword: keyword === '' ? null : keyword,
//         contentId: prevContentTypeId,
//         pageNo: pageParam,
//       },
//     },
//   );
//   return data;
// };

export const placeListFetcher = async ({
  pageParam,
  keyword,
  prevContentTypeId,
  regionId,
}: Props) => {
  const { data } = await axios.get(
    `https://6edb4ad1-bd38-4f7b-b4ff-b64db80e4610.mock.pstmn.io/places?regionId=${regionId}`,
    {
      params: {
        keyword: keyword === '' ? null : keyword,
        contentId: prevContentTypeId,
        pageNo: pageParam,
      },
    },
  );
  return data;
};
