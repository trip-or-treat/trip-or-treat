import { atom } from 'recoil';

const contentTypeIdAtom = atom<number | null>({
  key: 'contentTypeIdAtom',
  default: null,
});

export default contentTypeIdAtom;
