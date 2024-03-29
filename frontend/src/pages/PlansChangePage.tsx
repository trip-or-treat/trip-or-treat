import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import curDayAtom from 'src/atoms/curDayAtom';
import loginStateAtom from 'src/atoms/Login/loginStateAtom';
import { planTitleAtom } from 'src/atoms/plan';

import usePageState from 'src/hooks/usePageState';
import { usePlanDetail } from 'src/hooks/api/usePlanDetail';

import { SchedulesTypeWithRegions } from 'src/@types/api/planDetail';

import Loading from 'src/components/common/Loading';
import PlanChangeModal from 'src/components/mypage/PlanChangeModal';
import PlanNavBar from '../components/plan/PlanNavBar';
import PlansList from '../components/plan/PlansList';
import CommonButton from '../components/common/CommonButton';

import FailDataPage from './FailDataPage';

interface PlanDetailData {
  data: {
    data: SchedulesTypeWithRegions;
  };
  isLoading: boolean;
  isError: boolean;
}

const PlansChangePage = () => {
  const [open, setOpen] = useState(false);

  const navigate = useNavigate();
  const { planId } = useParams();

  const isLoggedIn = useRecoilValue(loginStateAtom);
  const setCurDay = useSetRecoilState(curDayAtom);
  const setTitle = useSetRecoilState(planTitleAtom);

  const { setClickedIdList, setTotalPlan } = usePageState();
  const { data: planDetailApi, isLoading, isError }: PlanDetailData = usePlanDetail(planId);

  useEffect(() => {
    const formattedData = planDetailApi?.data.schedules.map((data) => {
      return { date: data.date, day: data.scheduleId, items: data.schedulePlaces };
    });

    setCurDay(1);
    setTitle(planDetailApi?.data.title);
    setTotalPlan(formattedData);
    setClickedIdList(formattedData?.map((data) => data?.items));
  }, [planDetailApi]);

  const onClose = () => {
    setOpen(false);
    setTitle(planDetailApi?.data.title);
  };

  if (isError) {
    return (
      <Main>
        <FailDataPage />
      </Main>
    );
  }

  return (
    <Main>
      {isLoading && <Loading type="LARGE" />}
      {!isLoading && (
        <Wrapper>
          <PlanNavBar
            startDate={planDetailApi?.data.startDate}
            endDate={planDetailApi?.data.endDate}
            regions={planDetailApi?.data.regions.regionIds}
            schedules={planDetailApi?.data.schedules}
          />

          <PlansList schedules={planDetailApi?.data?.schedules} />

          <ButtonBox>
            <CommonButton onClick={() => setOpen(true)}>수정</CommonButton>
            <CommonButton onClick={() => navigate('/mypage/myPlan')}>목록</CommonButton>
          </ButtonBox>
        </Wrapper>
      )}

      {isLoggedIn && open && <PlanChangeModal detailData={planDetailApi?.data} onClose={onClose} />}
    </Main>
  );
};

export default PlansChangePage;

const Main = styled.main`
  position: fixed;

  width: 100vw;

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
