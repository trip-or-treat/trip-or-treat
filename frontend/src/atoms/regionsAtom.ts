import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

import { Regions } from 'src/@types/api/regions';

const { persistAtom } = recoilPersist();

const regionsAtom = atom<Regions[]>({
  key: 'regionsAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default regionsAtom;
