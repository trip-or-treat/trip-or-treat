import { HttpResponse, http } from 'msw';

import { END_POINTS } from 'src/constants/api';
import { regionsData } from '../data/regions';

export const regionsHandler = [
  http.get(END_POINTS.REGIONS, () => {
    return HttpResponse.json(regionsData);
  }),
];
