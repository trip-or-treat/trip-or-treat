import { useQuery } from '@tanstack/react-query';

import { regionsMoreInformationFetcher } from 'src/api/regionsMoreInformation';

export const useRegionsMoreInformation = (id: number) => {
  const { data, isError } = useQuery({
    queryKey: ['RegionsMoreInformation'],
    queryFn: () => regionsMoreInformationFetcher(id),
  });

  return { data, isError };
};
