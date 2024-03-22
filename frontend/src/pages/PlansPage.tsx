import { useState } from 'react';
import { useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import totalPlanAtom from 'src/atoms/totalPlanAtom';
import loginStateAtom from 'src/atoms/loginStateAtom';

import AlertModal from 'src/components/AlertModal';
import ConfirmSaveModal from 'src/components/ConfirmSaveModal';
import CommonButton from 'src/components/common/CommonButton';
import PlanNavBar from 'src/components/plan/PlanNavBar';
import PlansList from 'src/components/plan/PlansList';

import FailDataPage from './FailDataPage';

const PlansPage = () => {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();

  const myRegionList = useRecoilValue(myRegionListAtom);
  const totalPlan = useRecoilValue(totalPlanAtom);
  const isLoggedIn = useRecoilValue(loginStateAtom);

  const onClose = () => {
    setOpen(false);
  };

  if (!totalPlan[0]) return <FailDataPage />;

  return (
    <Main>
      <Wrapper>
        <PlanNavBar totalPlan={totalPlan} myRegionList={myRegionList} />
        <PlansList totalPlan={totalPlan} />

        <ButtonBox>
          <CommonButton onClick={() => setOpen(true)}>저장할래요!</CommonButton>
          <CommonButton onClick={() => navigate(`/place/${myRegionList[0]?.id}`)}>
            더 수정할래요!
          </CommonButton>
        </ButtonBox>
      </Wrapper>
      {isLoggedIn && open && <ConfirmSaveModal onClose={onClose} />}
      {!isLoggedIn && open && (
        <AlertModal onButtonText="로그인" offButtonText="닫기" path="/login" onClose={onClose}>
          저장하려면 로그인이 필요해요!
        </AlertModal>
      )}
    </Main>
  );
};

export default PlansPage;

const Main = styled.main`
  position: fixed;

  width: 100vw;
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});

  margin-top: ${(props) => props.theme.height.topNavHeight};
  padding: 40px 75px;

  box-sizing: border-box;
`;

const Wrapper = styled.div`
  overflow: hidden;
  justify-content: center;
`;

const ButtonBox = styled.div`
  display: flex;
  justify-content: center;

  margin-top: 40px;

  button {
    width: 200px;
    height: 50px;

    margin: 0px 10px;

    font-size: 18px;
    cursor: pointer;

    &:last-child {
      background-color: ${(props) => props.theme.colors.darkGrey};
    }
  }
`;
