import { useParams } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import placeClickedIdListAtom from 'src/atoms/placeClickedIdListAtom';
import { clickedIdListDetailAtom, totalPlanDetailAtom } from 'src/atoms/planDetail';
import totalPlanAtom from 'src/atoms/totalPlanAtom';

const usePageState = () => {
  const { regionId } = useParams();
  const [clickedIdList, setClickedIdList] = useRecoilState(
    regionId ? placeClickedIdListAtom : clickedIdListDetailAtom,
  );
  const [totalPlan, setTotalPlan] = useRecoilState(regionId ? totalPlanAtom : totalPlanDetailAtom);

  return {
    regionId,
    clickedIdList,
    setClickedIdList,
    totalPlan,
    setTotalPlan,
  };
};

export default usePageState;
