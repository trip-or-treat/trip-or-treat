import { END_POINTS } from 'src/constants/api';
import { loginApi } from '.';

interface Props {
  pageParam: number;
  searchType: string;
  keyword?: string;
  comingYn: string;
  createdYear: number | null;
}

export const myPlanListFetcher = async ({
  pageParam,
  searchType,
  keyword,
  comingYn,
  createdYear,
}: Props) => {
  try {
    const { data } = await loginApi.get(END_POINTS.PLANS, {
      params: {
        page: pageParam,
        type: searchType,
        keyword: keyword === '' ? null : keyword,
        comingYn,
        year: createdYear,
      },
    });

    return data;
  } catch (error) {
    console.error('데이터 불러오기 실패:', error);
    throw error;
  }
};
