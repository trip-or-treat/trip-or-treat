import { atom } from 'recoil';

const curDayAtom = atom<number>({
  key: 'curDayAtom',
  default: 1,
});

export default curDayAtom;
