import { atom } from 'recoil';

import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();
const contentTypeIdAtom = atom<number | null>({
  key: 'contentTypeIdAtom',
  default: null,
  effects_UNSTABLE: [persistAtom],
});

export default contentTypeIdAtom;
