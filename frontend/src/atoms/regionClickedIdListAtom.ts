import { atom } from 'recoil';

const regionClickedIdListAtom = atom<number[]>({
  key: 'regionClickedIdListAtom',
  default: [],
});

export default regionClickedIdListAtom;
