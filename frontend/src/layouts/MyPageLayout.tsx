import { Outlet } from 'react-router-dom';
import styled from 'styled-components';

import Nav from 'src/components/common/Nav';
import MyPageNavBar from 'src/components/mypage/MyPageNavBar';

const MyPageLayout = () => {
  return (
    <>
      <Nav />
      <MyPageNavBar />
      <Main>
        <Outlet />
      </Main>
    </>
  );
};

export default MyPageLayout;

const Main = styled.main`
  position: fixed;

  margin-top: 110px;
  margin-left: 34%;
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;
