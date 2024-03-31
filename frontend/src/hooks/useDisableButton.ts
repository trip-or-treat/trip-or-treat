import { useEffect, useState } from 'react';
import usePageState from './usePageState';

export const useDiableButton = () => {
  const { totalPlan } = usePageState();
  const [clickPossible, setClickPossible] = useState(false);

  useEffect(() => {
    setClickPossible(totalPlan.map((data) => data?.items).every((data) => data.length !== 0));
  }, [totalPlan]);

  return clickPossible;
};
