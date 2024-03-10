import styled from 'styled-components';
import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';

import homeModalAtom from 'src/atoms/homeModalAtom';
import LogoButton from './LogoButton';
import MenuButton from './MenuButton';

const Nav = () => {
  const setIsModal = useSetRecoilState(homeModalAtom);

  useEffect(() => {
    setIsModal(false);
  }, []);

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
  display: block;
  position: fixed;
  top: 0px;

  width: 100%;
  height: ${(props) => props.theme.height.topNavHeight};

  background-color: ${(props) => props.theme.colors.commonNavBgColor};

  line-height: ${(props) => props.theme.height.topNavHeight};
`;
