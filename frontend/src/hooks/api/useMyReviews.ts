import { useQuery } from '@tanstack/react-query';

import { myReviewsFetcher } from 'src/api/myReviews';

export const useReviews = () => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['reviews'],
    queryFn: () => myReviewsFetcher(),
  });

  return { data, isLoading, isError };
};
