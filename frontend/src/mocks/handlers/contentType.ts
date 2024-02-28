import { HttpResponse, http } from 'msw';

import { END_POINTS } from 'src/constants/api';
import { contentTypeData } from '../data/contentType';

export const contentTypeHandler = [
  http.get(END_POINTS.CONTENT_TYPE, () => {
    return HttpResponse.json(contentTypeData);
  }),
];
