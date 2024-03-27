import { useQuery } from '@tanstack/react-query';

import { reviewFetcher } from 'src/api/reviews';

export const useReview = (placeId: number) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['review', placeId],
    queryFn: () => reviewFetcher(placeId),
  });

  return { data, isLoading, isError };
};
