import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

const regionClickedIdListAtom = atom<number[]>({
  key: 'regionClickedIdListAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default regionClickedIdListAtom;
