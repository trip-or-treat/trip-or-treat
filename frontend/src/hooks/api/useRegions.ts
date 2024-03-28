import { useQuery } from '@tanstack/react-query';

import { regionsFetcher } from 'src/api/regions';

export const useRegions = () => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['regions'],
    queryFn: regionsFetcher,
  });

  return { data, isLoading, isError };
};
