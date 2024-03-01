import { contentTypeHandler } from './contentType';
import { regionsHandler } from './regions';

export const handlers = [...contentTypeHandler, ...regionsHandler];
