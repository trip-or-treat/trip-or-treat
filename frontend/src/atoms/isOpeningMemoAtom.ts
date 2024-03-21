import { atom } from 'recoil';

const isOpeningMemoAtom = atom<boolean>({
  key: 'isOpeningMemoAtom',
  default: false,
});

export default isOpeningMemoAtom;
