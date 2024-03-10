import { atom } from 'recoil';

const homeModalAtom = atom<boolean>({
  key: 'homeModalAtom',
  default: false,
});

export default homeModalAtom;
