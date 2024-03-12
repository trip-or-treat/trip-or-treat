import { atom } from 'recoil';

const dateSelectStateAtom = atom<boolean>({
  key: 'dateSelectStateAtom',
  default: false,
});

export default dateSelectStateAtom;
