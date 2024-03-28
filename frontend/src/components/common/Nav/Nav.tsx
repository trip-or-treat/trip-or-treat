import styled from 'styled-components';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useRecoilState, useSetRecoilState } from 'recoil';

import homeModalAtom from 'src/atoms/homeModalAtom';
import loginStateAtom from 'src/atoms/Login/loginStateAtom';

import KAKAO_AUTH_URL from 'src/components/KaKaoLogin/KakaoPath';
import LogoButton from './LogoButton';
import MenuButton from './MenuButton';

const Nav = () => {
  const setIsModal = useSetRecoilState(homeModalAtom);
  const [isLogin, setLogin] = useRecoilState(loginStateAtom);
  const { pathname } = useLocation();

  useEffect(() => {
    if (localStorage.getItem('accessToken')) {
      setLogin(true);
    }
    setIsModal(false);
  }, []);

  useEffect(() => {
    localStorage.setItem('prevPage', pathname);
  }, [pathname]);

  const onLogout = () => {
    alert('로그아웃 되었습니다.');
    localStorage.removeItem('accessToken');
    setLogin(false);
  };

  return (
    <NavBox>
      {isLogin ? (
        <>
          <LogoButton />
          <MenuButton path="/" onClick={onLogout}>
            로그아웃
          </MenuButton>
          <MenuButton path="/">마이페이지</MenuButton>
          <MenuButton path="/">이용방법</MenuButton>
        </>
      ) : (
        <>
          <LogoButton />
          <MenuButton path={KAKAO_AUTH_URL}>로그인</MenuButton>
          <MenuButton path="/">이용방법</MenuButton>
        </>
      )}
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
