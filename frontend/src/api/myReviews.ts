import { END_POINTS } from 'src/constants/api';

import { loginApi } from '.';

export const myReviewsFetcher = async () => {
  const { data } = await loginApi.get(`${END_POINTS.REVIEWS}?page=1`);
  console.log(data);
  return data;
};
