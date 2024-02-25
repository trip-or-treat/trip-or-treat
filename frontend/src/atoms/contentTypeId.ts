import { atom } from 'recoil';

const contentTypeId = atom<string | null>({
  key: 'contentTypeIdAtom',
  default: null,
});

export default contentTypeId;
