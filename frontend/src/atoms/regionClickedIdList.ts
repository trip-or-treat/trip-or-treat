import { atom } from 'recoil';

const regionClickedIdList = atom<number[]>({
  key: 'regionClickedIdListAtom',
  default: [],
});

export default regionClickedIdList;
