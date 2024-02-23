import { atom } from 'recoil';

const stepPlanSavedBtn = atom({
  key: 'stepPlanSavedAtom',
  default: false,
});

export default stepPlanSavedBtn;
