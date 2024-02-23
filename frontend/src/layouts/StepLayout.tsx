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
  margin-top: ${(props) => props.theme.height.topNavHeight};
  margin-left: ${(props) => props.theme.width.leftNavWidth};
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;
