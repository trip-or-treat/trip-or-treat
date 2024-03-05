import { atom } from 'recoil';
import { Regions } from 'src/@types/api/regions';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

const regionsAtom = atom<Regions[]>({
  key: 'regionsAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default regionsAtom;
