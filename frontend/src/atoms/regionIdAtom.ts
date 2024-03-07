import { atom } from 'recoil';

const regionIdAtom = atom<number>({
  key: 'regionIdAtom',
  default: 0,
});

export default regionIdAtom;
