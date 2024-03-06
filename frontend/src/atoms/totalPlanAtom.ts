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
    { day: 1, date: '02 21', items: [] },
    { day: 2, date: '02 22', items: [] },
    { day: 3, date: '02 23', items: [] },
    { day: 4, date: '02 24', items: [] },
    { day: 5, date: '02 25', items: [] },
    { day: 6, date: '02 26', items: [] },
    { day: 7, date: '02 27', items: [] },
  ],
  effects_UNSTABLE: [persistAtom],
});

export default totalPlanAtom;
