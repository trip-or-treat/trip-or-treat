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
  default: [
    { day: 1, date: '2024-02-13', items: [] },
    { day: 2, date: '2024-02-22', items: [] },
    { day: 3, date: '2024-02-23', items: [] },
  ],
  effects_UNSTABLE: [persistAtom],
});

export default totalPlanAtom;
