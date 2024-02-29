import { atom } from 'recoil';
import { Regions } from 'src/@types/api/regions';

const myRegionListAtom = atom<Regions[]>({
  key: 'myRegionListAtom',
  default: [],
});

export default myRegionListAtom;
