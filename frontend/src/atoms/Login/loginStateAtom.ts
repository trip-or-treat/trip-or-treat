import { atom } from 'recoil';

const loginStateAtom = atom<boolean>({
  key: 'loginStateAtom',
  default: false,
});

export default loginStateAtom;
