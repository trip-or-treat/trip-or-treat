import { ReviewWrite } from 'src/@types/api/reviewWrite';

import { END_POINTS } from 'src/constants/api';

import { loginApi } from '.';

export const reviewWriteFetcher = async ({ placeId, content, tip, score }: ReviewWrite) => {
  const review = {
    placeId,
    content,
    tip,
    score,
  };

  const data = await loginApi.post(END_POINTS.REVIEWS, review);

  return data;
};
