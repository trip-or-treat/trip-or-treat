import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import Loading from 'src/components/common/Loading';
import PlanNavBar from 'src/components/plan/PlanNavBar';
import PlansList from 'src/components/plan/PlansList';
import { useShare } from 'src/hooks/api/useShare';

import FailDataPage from './FailDataPage';

const SharedPage = () => {
  const { code } = useParams();

  const { data: shareApi, isLoading, isError } = useShare(code ?? '');

  if (isError) return <FailDataPage />;

  return (
    <Main>
      {isLoading && <Loading type="LARGE" />}
      {!isLoading && (
        <Wrapper>
          <PlanNavBar
            startDate={shareApi?.data.startDate}
            endDate={shareApi?.data.endDate}
            regions={shareApi?.data.regions.regionIds}
            schedules={shareApi?.data.schedules}
          />

          <PlansList schedules={shareApi?.data.schedules} />
        </Wrapper>
      )}
    </Main>
  );
};

export default SharedPage;

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
