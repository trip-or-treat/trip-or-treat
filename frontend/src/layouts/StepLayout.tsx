import { useEffect } from 'react';
import { Outlet, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';

import Nav from 'src/components/common/Nav';
import StepNavBar from 'src/components/StepNavBar';

import AlertModal from 'src/components/AlertModal';
import regionsAtom from 'src/atoms/regionsAtom';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import regionClickedIdListAtom from 'src/atoms/regionClickedIdListAtom';
import homeModalAtom from 'src/atoms/homeModalAtom';

const StepLayout = () => {
  const { regionId } = useParams();
  const regions = useRecoilValue(regionsAtom);
  const [myRegionList, setMyRegionList] = useRecoilState(myRegionListAtom);
  const [isModal, setIsModal] = useRecoilState(homeModalAtom);
  const setClickRegionListId = useSetRecoilState(regionClickedIdListAtom);

  const mainRegion = regions.find((region) => region.id === Number(regionId));

  const onClose = () => {
    setIsModal(false);
  };

  useEffect(() => {
    if (mainRegion && myRegionList.length < 1) {
      setMyRegionList([mainRegion]);
      setClickRegionListId([mainRegion.id]);
    }
  }, [regionId]);

  return (
    <>
      <Nav />
      <StepNavBar />
      <Main>
        <Outlet />
      </Main>
      {isModal && <AlertModal onClose={onClose} />}
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
