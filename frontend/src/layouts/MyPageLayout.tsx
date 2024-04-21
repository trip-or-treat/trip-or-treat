import { Outlet } from 'react-router-dom';
import styled from 'styled-components';

import Nav from 'src/components/common/Nav';
import MyPageNavBar from 'src/components/mypage/MyPageNavBar';
import media from 'src/styles/media';

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
  width: 850px;
  height: calc(100vh - 110px);
  margin-top: 110px;
  margin-left: 34%;

  ${media.mobile`
    width: 100%;
    height: calc(100vh - 270px);
    margin: 190px 0;
    padding: 0 20px;
  `}
`;
