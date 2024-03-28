import { useQuery } from '@tanstack/react-query';

import { reviewFetcher } from 'src/api/review';

export const useReview = (placeId: number) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['review', placeId],
    queryFn: () => reviewFetcher(placeId),
  });

  return { data, isLoading, isError };
};
