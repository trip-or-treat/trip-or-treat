import { useQuery } from '@tanstack/react-query';

import { regionsMoreInformationFetcher } from 'src/api/regionsMoreInformation';

export const useRegionsMoreInformation = (id: number) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['RegionsMoreInformation', id],
    queryFn: () => regionsMoreInformationFetcher(id),
  });

  return { data, isLoading, isError };
};
