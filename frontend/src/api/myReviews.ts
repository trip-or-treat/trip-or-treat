import { END_POINTS } from 'src/constants/api';

import { loginApi } from '.';

interface Props {
  pageParam: number;
}

export const myReviewsFetcher = async ({ pageParam }: Props) => {
  const { data } = await loginApi.get(END_POINTS.REVIEWS, {
    params: {
      page: pageParam,
    },
  });

  return data;
};
