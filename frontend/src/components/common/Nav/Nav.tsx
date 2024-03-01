import styled from 'styled-components';

import LogoButton from './LogoButton';
import MenuButton from './MenuButton';

const Nav = () => {
  return (
    <NavBox>
      <LogoButton />
      <MenuButton path="/">로그인</MenuButton>
      <MenuButton path="/">이용방법</MenuButton>
    </NavBox>
  );
};

export default Nav;

const NavBox = styled.div`
  position: fixed;

  display: block;

  width: 100%;
  top: 0px;

  height: ${(props) => props.theme.height.topNavHeight};

  background-color: ${(props) => props.theme.colors.commonNavBgColor};

  line-height: ${(props) => props.theme.height.topNavHeight};
`;
