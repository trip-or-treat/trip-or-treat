import { useQuery } from '@tanstack/react-query';
import { shareFetcher } from 'src/api/share';

export const useShare = (code: string) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['RegionsMoreInformation', code],
    queryFn: () => shareFetcher(code),
  });

  return { data, isLoading, isError };
};
