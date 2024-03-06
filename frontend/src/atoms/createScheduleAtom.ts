import { atom } from 'recoil';

const createScheduleAtom = atom<boolean>({
  key: 'createScheduleAtom',
  default: true,
});

export default createScheduleAtom;
