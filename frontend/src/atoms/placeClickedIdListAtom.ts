import { atom } from 'recoil';

import { PlaceListTypes } from 'src/@types/api/placeList';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

const placeClickedIdListAtom = atom<PlaceListTypes[][]>({
  key: 'placeClickedIdListAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default placeClickedIdListAtom;
