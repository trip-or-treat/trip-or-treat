import { contentTypeHandler } from './contentType';
import { placeListHandler } from './placeList';
import { regionsHandler } from './regions';

export const handlers = [...contentTypeHandler, ...regionsHandler, ...placeListHandler];
