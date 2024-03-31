import { END_POINTS } from 'src/constants/api';

import { ReviewWrite } from 'src/@types/api/reviewWrite';

import { api, loginApi } from '.';

export const reviewFetcher = async (placeId: number) => {
  const { data } = await api.get(`${END_POINTS.PLACES}/${placeId}/review`);
  return data;
};

export const editReviewFetcher = async ({ id, placeId, content, tip, score }: ReviewWrite) => {
  const review = {
    placeId,
    content,
    tip,
    score,
  };

  const data = await loginApi.put(`${END_POINTS.REVIEWS}/${id}`, review);

  return data;
};

export const deleteReviewFetcher = async (id: number) => {
  const data = await loginApi.delete(`${END_POINTS.REVIEWS}/${id}`);

  return data;
};
