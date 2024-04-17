import { useQuery } from '@tanstack/react-query';
import { userInfoFetcher } from 'src/api/userInfoFetcher';

export const useUserInfo = () => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['userInfo'],
    queryFn: userInfoFetcher,
    staleTime: 0,
    gcTime: 3000,
  });

  return { data, isLoading, isError };
};
