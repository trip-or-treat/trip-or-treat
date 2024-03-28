import { contentTypeHandler } from './contentType';
import { placeListHandler } from './placeList';
import { regionsHandler } from './regions';
import { regionsMoreInformationHandler } from './regionsMoreInformation';

export const handlers = [
  ...contentTypeHandler,
  ...regionsHandler,
  ...placeListHandler,
  ...regionsMoreInformationHandler,
];
