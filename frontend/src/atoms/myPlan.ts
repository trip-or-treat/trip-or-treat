import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';
import { Plan } from 'src/@types/api/plan';

const { persistAtom } = recoilPersist();

const myPlanCreatedYearAtom = atom<number | null>({
  key: 'myPlanCreatedYearAtom',
  default: null,
});

const myPlanSearchTypeAtom = atom<string>({
  key: 'myPlanSearchTypeAtom',
  default: 'T',
  effects_UNSTABLE: [persistAtom],
});

const myPlanComingYnAtom = atom<string>({
  key: 'myPlanComingYnAtom',
  default: 'Y',
  effects_UNSTABLE: [persistAtom],
});

const myPlanCurrentPageAtom = atom<number>({
  key: 'myPlanCurrentPageAtom',
  default: 1,
  effects_UNSTABLE: [persistAtom],
});

const myPlanCurrentPostAtom = atom<Plan[]>({
  key: 'myPlanCurrentPostAtom',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export {
  myPlanCreatedYearAtom,
  myPlanSearchTypeAtom,
  myPlanComingYnAtom,
  myPlanCurrentPageAtom,
  myPlanCurrentPostAtom,
};
