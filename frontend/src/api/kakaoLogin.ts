import { api } from '.';

export const kakaoLoginFetcher = async (code: string | null) => {
  const data = await api.get(`/login?code=${code}`);
  return data.headers.authorization;
};
