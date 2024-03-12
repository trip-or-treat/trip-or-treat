import { atom } from 'recoil';

import { ContentType } from 'src/@types/api/contentType';

const contentTypelistAtom = atom<ContentType[]>({
  key: 'contentTypelistAtom',
  default: [],
});

export default contentTypelistAtom;
