import { atom } from 'recoil';

import { ContentType } from 'src/@types/api/contentType';

const placeIdAtom = atom<ContentType>({
  key: 'palceIdAtom',
  default: { id: 0, name: '' },
});

export default placeIdAtom;
