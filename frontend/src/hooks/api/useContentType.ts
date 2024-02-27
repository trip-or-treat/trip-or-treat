import { useQuery } from '@tanstack/react-query';
import { contentTypeFetcher } from 'src/api/contentType';

export const useContentType = () => {
  const { data, isLoading } = useQuery({
    queryKey: ['contentType'],
    queryFn: contentTypeFetcher,
    staleTime: Infinity,
  });

  return { data, isLoading };
};
