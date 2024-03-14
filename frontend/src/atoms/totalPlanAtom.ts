import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

import { PlaceListTypes } from 'src/@types/api/placeList';

export interface TotalPlan {
  day: number;
  date: string;
  items: PlaceListTypes[];
}

const { persistAtom } = recoilPersist();

const totalPlanAtom = atom<TotalPlan[]>({
  key: 'totalPlanAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export default totalPlanAtom;
