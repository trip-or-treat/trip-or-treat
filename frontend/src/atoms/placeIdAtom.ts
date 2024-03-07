import { atom } from 'recoil';

const palceIdAtom = atom<number>({
  key: 'palceIdAtom',
  default: 0,
});

export default palceIdAtom;
