import { atom } from 'recoil';
import { PlaceListTypes } from 'src/@types/api/placeList';
import { SchedulePlacesDetail } from 'src/@types/api/schedules';

export interface TotalPlanDetail {
  day: number;
  date: string;
  items: SchedulePlacesDetail[] | PlaceListTypes[];
}

export const planDetailCurRegionIdAtom = atom<number>({
  key: 'planDetailCurRegionIdAtom',
  default: 0,
});

export const totalPlanDetailAtom = atom<TotalPlanDetail[]>({
  key: 'totalPlanDetailAtom',
  default: [],
});

export const clickedIdListDetailAtom = atom<SchedulePlacesDetail[][] | PlaceListTypes[][]>({
  key: 'clickedIdListDetailAtom',
  default: [],
});
