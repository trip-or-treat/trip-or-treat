import { useQuery } from '@tanstack/react-query';

import { placeInfoFetcher } from 'src/api/placeInfo';

export const usePlaceInfo = (id: number) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['placeInfo', id],
    queryFn: () => placeInfoFetcher(id),
  });

  return { data, isLoading, isError };
};
