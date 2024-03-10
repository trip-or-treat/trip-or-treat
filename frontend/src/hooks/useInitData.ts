import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';
import curDayAtom from 'src/atoms/curDayAtom';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import placeClickedIdListAtom from 'src/atoms/placeClickedIdListAtom';
import regionClickedIdListAtom from 'src/atoms/regionClickedIdListAtom';
import totalPlanAtom from 'src/atoms/totalPlanAtom';

const useInitData = () => {
  const setTotalPlan = useSetRecoilState(totalPlanAtom);
  const setMyRegionList = useSetRecoilState(myRegionListAtom);
  const setRegionClickedId = useSetRecoilState(regionClickedIdListAtom);
  const setPlaceClickedId = useSetRecoilState(placeClickedIdListAtom);
  const setPrevContentTypeId = useSetRecoilState(contentTypeIdAtom);
  const setCurDay = useSetRecoilState(curDayAtom);

  useEffect(() => {
    setPlaceClickedId([]);
    setMyRegionList([]);
    setRegionClickedId([]);
    setPrevContentTypeId(null);
    setCurDay(1);
    setTotalPlan([
      { day: 1, date: '2024-02-13', items: [] },
      { day: 2, date: '2024-02-22', items: [] },
      { day: 3, date: '2024-02-23', items: [] },
    ]);
  }, []);
};
export default useInitData;
