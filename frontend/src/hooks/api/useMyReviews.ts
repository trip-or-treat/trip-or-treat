import { useQuery } from '@tanstack/react-query';

import { myReviewsFetcher } from 'src/api/myReviews';

interface Props {
  pageParam: number;
}

export const useReviews = ({ pageParam }: Props) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['reviews', pageParam],
    queryFn: () => myReviewsFetcher({ pageParam }),
  });

  return { data, isLoading, isError };
};
