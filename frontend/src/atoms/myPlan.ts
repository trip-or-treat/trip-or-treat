import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

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

const myPlanComingYnAtom = atom<string | null>({
  key: 'myPlanComingYnAtom',
  default: null,
  effects_UNSTABLE: [persistAtom],
});

const myPlanCurrentPageAtom = atom<number>({
  key: 'myPlanCurrentPageAtom',
  default: 1,
  effects_UNSTABLE: [persistAtom],
});

export { myPlanCreatedYearAtom, myPlanSearchTypeAtom, myPlanComingYnAtom, myPlanCurrentPageAtom };
