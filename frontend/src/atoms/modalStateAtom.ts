import { atom } from 'recoil';

const modalStateAtom = atom<boolean>({
  key: 'modalStateAtom',
  default: false,
});

export default modalStateAtom;
