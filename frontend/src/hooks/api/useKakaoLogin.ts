import { useQuery } from '@tanstack/react-query';

import { kakaoLoginFetcher } from 'src/api/kakaoLogin';

export const useKakaoLogin = (code: string | null) => {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['login', code],
    queryFn: () => kakaoLoginFetcher(code),
  });

  return { data, isLoading, isError };
};
