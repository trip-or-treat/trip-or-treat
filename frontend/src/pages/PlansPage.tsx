import { useState } from 'react';
import { useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import totalPlanAtom from 'src/atoms/totalPlanAtom';

import LoginModal from 'src/components/LoginModal';
import ConfirmSaveModal from 'src/components/ConfirmSaveModal';
import CommonButton from 'src/components/common/CommonButton';
import PlansList from 'src/components/PlansList';
import FailDataPage from './FailDataPage';

const parseName = (name: string) => {
  return name.length > 5 ? name.slice(0, 2) : name;
};

const PlansPage = () => {
  const myRegionList = useRecoilValue(myRegionListAtom);
  const totalPlan = useRecoilValue(totalPlanAtom);
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const isLoggedIn = false;

  const onClose = () => {
    setOpen(false);
  };

  if (!totalPlan[0]) return <FailDataPage />;

  return (
    <Main>
      <Wrapper>
        <TitleAndDateBox>
          <ul>{myRegionList?.map((item) => <li key={item.id}>{parseName(item.name)}</li>)}</ul>
          <p> {`${totalPlan[0]?.date} ~ ${totalPlan[totalPlan.length - 1]?.date}`}</p>
        </TitleAndDateBox>

        <PlansList />

        <ButtonBox>
          <CommonButton onClick={() => setOpen(true)}>저장할래요!</CommonButton>
          <CommonButton onClick={() => navigate(`/place/${myRegionList[0]?.id}`)}>
            더 수정할래요!
          </CommonButton>
        </ButtonBox>
      </Wrapper>
      {isLoggedIn && open && <ConfirmSaveModal onClose={onClose} />}
      {!isLoggedIn && open && <LoginModal onClose={onClose} />}
    </Main>
  );
};

export default PlansPage;
const Main = styled.main`
  position: fixed;

  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
  width: 100vw;
  margin-top: ${(props) => props.theme.height.topNavHeight};
  padding: 50px 75px;

  box-sizing: border-box;
`;

const Wrapper = styled.div`
  overflow: hidden;
`;

const TitleAndDateBox = styled.div`
  display: flex;
  align-items: flex-end;
  width: 30%;

  ul {
    display: flex;
  }

  p {
    font-size: 15px;
    font-family: 'Pretendard-Medium';
    color: ${(props) => props.theme.colors.darkGrey};
  }

  li {
    margin-right: 20px;

    color: ${(props) => props.theme.colors.blackFont};
    font-size: 25px;
    font-family: 'Pretendard-Bold';
  }
`;

const ButtonBox = styled.div`
  display: flex;
  justify-content: center;

  margin-top: 50px;

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
