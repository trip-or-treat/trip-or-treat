import { atom } from 'recoil';

const contentTypeId = atom<number | null>({
  key: 'contentTypeIdAtom',
  default: null,
});

export default contentTypeId;
