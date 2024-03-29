import { useQuery } from '@tanstack/react-query';

import { myPlanListFetcher } from 'src/api/myPlanList';
import { Plan } from 'src/@types/api/plan';

interface MyPlanListResponse {
  data: Plan[];
  isLoading: boolean;
  isError: boolean;
}

interface Props {
  pageParam: number;
  searchType: string;
  keyword?: string;
  comingYn: string;
  createdYear: number;
  token: string;
}

const useMyPlanList = ({
  pageParam,
  searchType,
  keyword,
  comingYn,
  createdYear,
  token,
}: Props): MyPlanListResponse => {
  const { data, isLoading, isError } = useQuery<Plan[], Error>({
    queryKey: ['myPlanList'],
    queryFn: () =>
      myPlanListFetcher({ pageParam, searchType, keyword, comingYn, createdYear, token }),
  });

  const myPlanListData = data ?? [];

  return { data: myPlanListData, isLoading, isError };
};

export default useMyPlanList;
