import { atom } from 'recoil';
import { Regions } from 'src/@types/api/regions';

const regionsAtom = atom<Regions[]>({
  key: 'regionsAtom',
  default: [],
});

export default regionsAtom;
