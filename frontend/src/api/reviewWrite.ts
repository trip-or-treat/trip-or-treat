import { ReviewWrite } from 'src/@types/api/reviewWrite';
import { useLogin } from 'src/hooks/useLogin';

import { api } from '.';

export const reviewWriteFetcher = async ({ placeId, content, tip, score }: ReviewWrite) => {
  const { headers, tokenRenewal, loginError } = useLogin();

  const review = {
    placeId,
    content,
    tip,
    score,
  };

  const data = await api.post('/reviews', review, { headers });

  if (data.headers.authorization) {
    tokenRenewal(data.headers.authorization);
  }

  if (data.data.status === 401) {
    loginError();
  }

  return data;
};
