import { useQuery } from '@tanstack/react-query';

import { myPlanListFetcher } from 'src/api/myPlanList';

interface Props {
  pageParam: number;
  searchType: string;
  keyword?: string;
  comingYn: string | null;
  createdYear: number | null;
}

const useMyPlanList = ({ pageParam, searchType, keyword, comingYn, createdYear }: Props) => {
  const { data, isError, isLoading } = useQuery({
    queryKey: ['myPlanList', pageParam, searchType, keyword, comingYn, createdYear],
    queryFn: () => myPlanListFetcher({ pageParam, searchType, keyword, comingYn, createdYear }),
  });
  return { data, isError, isLoading };
};

export default useMyPlanList;
