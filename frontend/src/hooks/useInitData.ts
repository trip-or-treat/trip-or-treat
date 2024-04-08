import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';
import curDayAtom from 'src/atoms/curDayAtom';
import placeClickedIdListAtom from 'src/atoms/placeClickedIdListAtom';
import { planTitleAtom } from 'src/atoms/plan';
import regionClickedIdListAtom from 'src/atoms/regionClickedIdListAtom';
import totalPlanAtom from 'src/atoms/totalPlanAtom';

const useInitData = () => {
  const setTotalPlan = useSetRecoilState(totalPlanAtom);
  const setRegionClickedId = useSetRecoilState(regionClickedIdListAtom);
  const setPlaceClickedId = useSetRecoilState(placeClickedIdListAtom);
  const setPrevContentTypeId = useSetRecoilState(contentTypeIdAtom);
  const setCurDay = useSetRecoilState(curDayAtom);
  const setTitle = useSetRecoilState(planTitleAtom);

  useEffect(() => {
    setPlaceClickedId([]);
    setRegionClickedId([]);
    setPrevContentTypeId(null);
    setCurDay(1);
    setTotalPlan([]);
    setTitle('');
  }, []);
};

export default useInitData;
