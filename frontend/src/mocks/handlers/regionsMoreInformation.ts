import { HttpResponse, http } from 'msw';

import { END_POINTS } from 'src/constants/api';
import { regionsMoreInformationData } from 'src/mocks/data/regionsMoreInformation';

export const regionsMoreInformationHandler = [
  http.get(`${END_POINTS.REGIONS}/1`, () => {
    return HttpResponse.json(regionsMoreInformationData);
  }),
];
