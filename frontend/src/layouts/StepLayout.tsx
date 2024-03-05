import { Outlet, useParams } from 'react-router-dom';
import styled from 'styled-components';

import Nav from 'src/components/common/Nav';
import StepNavBar from 'src/components/StepNavBar';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import regionsAtom from 'src/atoms/regionsAtom';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import regionClickedIdListAtom from 'src/atoms/regionClickedIdListAtom';
import { useEffect } from 'react';

const StepLayout = () => {
  const { regionId } = useParams();
  const regions = useRecoilValue(regionsAtom);
  const mainRegion = regions.find((region) => region.id === Number(regionId));
  const [myRegionList, setMyRegionList] = useRecoilState(myRegionListAtom);
  const setClickRegionListId = useSetRecoilState(regionClickedIdListAtom);

  useEffect(() => {
    if (mainRegion && myRegionList.length <= 1) {
      setMyRegionList([mainRegion]);
      setClickRegionListId([mainRegion.id]);
    }
  }, []);

  return (
    <>
      <Nav />
      <StepNavBar />
      <Main>
        <Outlet />
      </Main>
    </>
  );
};

export default StepLayout;

const Main = styled.main`
  position: fixed;
  margin-top: ${(props) => props.theme.height.topNavHeight};
  margin-left: ${(props) => props.theme.width.leftNavWidth};
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;
