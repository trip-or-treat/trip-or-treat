import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

import { PlaceListTypes } from 'src/@types/api/placeList';

const { persistAtom } = recoilPersist();

const placeClickedIdListAtom = atom<PlaceListTypes[][]>({
  key: 'placeClickedIdListAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default placeClickedIdListAtom;
