import { Outlet } from 'react-router-dom';

import styled from 'styled-components';

const StepLayout = () => {
  return (
    <Main>
      <Outlet />
    </Main>
  );
};

export default StepLayout;

const Main = styled.main`
  margin-top: ${(props) => props.theme.height.commonNavHeight};
  margin-left: ${(props) => props.theme.width.stepNavWidth};
  height: calc(100vh - 78px);
`;
