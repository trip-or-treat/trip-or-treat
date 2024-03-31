import { atom } from 'recoil';

export const editReviewAtom = atom<boolean>({
  key: 'editReviewAtom',
  default: false,
});
