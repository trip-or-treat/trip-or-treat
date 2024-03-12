import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

import { ContentType } from 'src/@types/api/contentType';

const { persistAtom } = recoilPersist();

const contentTypelistAtom = atom<ContentType[]>({
  key: 'contentTypelistAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default contentTypelistAtom;
