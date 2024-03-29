import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

const myPlanCreatedYearAtom = atom<number>({
  key: 'myPlanCreatedYearAtom',
  default: 6,
  effects_UNSTABLE: [persistAtom],
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

export { myPlanCreatedYearAtom, myPlanSearchTypeAtom, myPlanComingYnAtom };
