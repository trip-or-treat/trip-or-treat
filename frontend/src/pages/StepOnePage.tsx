import { useRecoilValue } from 'recoil';

import myRegionListAtom from 'src/atoms/myRegionListAtom';

const StepOnePage = () => {
  const myRegionList = useRecoilValue(myRegionListAtom);

  return (
    <div>
      <div>{myRegionList[0].name}</div>
    </div>
  );
};

export default StepOnePage;
