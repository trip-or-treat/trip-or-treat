import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import styled from 'styled-components';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import KaKaoMap from 'src/components/KaKaoMap';
import ScheduleModal from 'src/components/ScheduleModal';
import useInitData from 'src/hooks/useInitData';
import { useParams } from 'react-router-dom';
import regionsAtom from 'src/atoms/regionsAtom';
import regionClickedIdListAtom from 'src/atoms/regionClickedIdListAtom';
import { useEffect } from 'react';

const StepOnePage = () => {
  const { regionId } = useParams();
  const regions = useRecoilValue(regionsAtom);
  const [myRegionList, setMyRegionList] = useRecoilState(myRegionListAtom);
  const setClickRegionListId = useSetRecoilState(regionClickedIdListAtom);
  const mainRegion = regions.find((region) => region.id === Number(regionId));
  useInitData();

  useEffect(() => {
    if (mainRegion) {
      setMyRegionList([mainRegion]);
      setClickRegionListId([mainRegion.id]);
    }
  }, [regionId]);

  return (
    <Wrapper>
      <ScheduleModal />
      <KaKaoMap list={myRegionList} />
    </Wrapper>
  );
};

export default StepOnePage;

const Wrapper = styled.div`
  display: flex;

  width: calc(100vw - ${(props) => props.theme.width.leftNavWidth});
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;
