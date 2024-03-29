import { useQuery } from '@tanstack/react-query';
import { planDetailFetcher } from 'src/api/planDetail';

export const usePlanDetail = (id: string | undefined) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['planDetail', id],
    queryFn: () => planDetailFetcher(id),
  });

  return { data, isLoading, isError };
};
