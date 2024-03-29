import { END_POINTS } from 'src/constants/api';
import { Plan } from 'src/@types/api/plan';
import { api } from '.';

interface Props {
  pageParam: number;
  searchType: string;
  keyword?: string;
  comingYn: string;
  createdYear: number;
  token: string;
}

export const myPlanListFetcher = async ({
  pageParam,
  searchType,
  keyword,
  comingYn,
  createdYear,
  token,
}: Props): Promise<Plan[]> => {
  try {
    const { data } = await api.get(END_POINTS.PLANS, {
      params: {
        page: pageParam,
        type: searchType,
        keyword: keyword === '' ? null : keyword,
        comingYn,
        year: createdYear,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return data;
  } catch (error) {
    console.error('데이터 불러오기 실패:', error);
    throw error;
  }
};
