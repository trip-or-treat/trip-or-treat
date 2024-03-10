import { useEffect } from 'react';
import { Outlet, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import Nav from 'src/components/common/Nav';
import StepNavBar from 'src/components/StepNavBar';

import regionsAtom from 'src/atoms/regionsAtom';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import regionClickedIdListAtom from 'src/atoms/regionClickedIdListAtom';

const StepLayout = () => {
  const { regionId } = useParams();
  const regions = useRecoilValue(regionsAtom);
  const mainRegion = regions.find((region) => region.id === Number(regionId));
  const setMyRegionList = useSetRecoilState(myRegionListAtom);
  const setClickRegionListId = useSetRecoilState(regionClickedIdListAtom);

  useEffect(() => {
    if (mainRegion) {
      setMyRegionList([mainRegion]);
      setClickRegionListId([mainRegion.id]);
    }
  }, [regions]);

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
