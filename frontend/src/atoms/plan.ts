import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const planTitleAtom = atom<string>({
  key: 'planTitleAtom',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const pastPlanAtom = atom<boolean>({
  key: 'pastPlanAtom',
  default: false,
});
