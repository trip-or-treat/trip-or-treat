import { contentTypeHandler } from './contentType';
import { regionsHandler } from './regions';
import { regionsMoreInformationHandler } from './regionsMoreInformation';

export const handlers = [
  ...contentTypeHandler,
  ...regionsHandler,
  ...regionsMoreInformationHandler,
];
