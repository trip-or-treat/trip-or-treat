import { useRecoilValue } from 'recoil';
import styled from 'styled-components';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import KaKaoMap from 'src/components/KaKaoMap';
import ScheduleModal from 'src/components/ScheduleModal';

const StepOnePage = () => {
  const myRegionList = useRecoilValue(myRegionListAtom);

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
