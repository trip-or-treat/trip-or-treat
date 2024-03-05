import { atom } from 'recoil';
import { Regions } from 'src/@types/api/regions';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

const myRegionListAtom = atom<Regions[]>({
  key: 'myRegionListAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default myRegionListAtom;
