import { useEffect } from 'react';
import { Outlet, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';

import regionsAtom from 'src/atoms/regionsAtom';
import dateSelectStateAtom from 'src/atoms/dateSelectStateAtom';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import regionClickedIdListAtom from 'src/atoms/regionClickedIdListAtom';
import homeModalAtom from 'src/atoms/homeModalAtom';

import Nav from 'src/components/common/Nav';
import StepNavBar from 'src/components/StepNavBar';
import AlertModal from 'src/components/AlertModal';

const StepLayout = () => {
  const { regionId } = useParams();
  const regions = useRecoilValue(regionsAtom);
  const [myRegionList, setMyRegionList] = useRecoilState(myRegionListAtom);
  const [isModal, setIsModal] = useRecoilState(homeModalAtom);
  const setClickRegionListId = useSetRecoilState(regionClickedIdListAtom);
  const [isDateSelect, setDateSelect] = useRecoilState(dateSelectStateAtom);

  const mainRegion = regions.find((region) => region.id === Number(regionId));

  const onClose = () => {
    setIsModal(false);
  };

  const CloseDate = () => {
    setDateSelect(false);
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
      {isModal && (
        <AlertModal path="/" onButtonText="홈으로" offButtonText="닫기" onClose={onClose}>
          계획 생성을 중단하시겠습니까?
          <br />
          변경사항은 저장되지 않습니다.
        </AlertModal>
      )}
      {isDateSelect && (
        <AlertModal
          path={`/date/${regionId}`}
          onButtonText="변경하기"
          offButtonText="닫기"
          onClose={CloseDate}
        >
          일정을 변경하시겠습니까?
          <br />
          선택사항은 저장되지 않습니다.
        </AlertModal>
      )}
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
