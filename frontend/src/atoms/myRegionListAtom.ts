import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

import { Regions } from 'src/@types/api/regions';

const { persistAtom } = recoilPersist();

const myRegionListAtom = atom<Regions[]>({
  key: 'myRegionListAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default myRegionListAtom;
