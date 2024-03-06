import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();
const createScheduleAtom = atom<boolean>({
  key: 'createScheduleAtom',
  default: true,
  effects_UNSTABLE: [persistAtom],
});

export default createScheduleAtom;
