import { atom } from 'recoil';

import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

const curDayAtom = atom<number>({
  key: 'curDayAtom',
  default: 1,
  effects_UNSTABLE: [persistAtom],
});

export default curDayAtom;
