import { atom } from 'recoil';

import { PlaceListTypes } from 'src/@types/api/placeList';

const placeClickedIdListAtom = atom<PlaceListTypes[][]>({
  key: 'placeClickedIdListAtom',
  default: [],
});

export default placeClickedIdListAtom;
