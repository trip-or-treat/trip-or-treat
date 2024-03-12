import { atom } from 'recoil';

import { ContentType } from 'src/@types/api/contentType';

const palceIdAtom = atom<ContentType>({
  key: 'palceIdAtom',
  default: { id: 0, name: '' },
});

export default palceIdAtom;
