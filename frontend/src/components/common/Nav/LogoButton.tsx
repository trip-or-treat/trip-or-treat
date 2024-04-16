import styled from 'styled-components';
import { Link, useLocation } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import media from 'src/styles/media';

import homeModalAtom from 'src/atoms/homeModalAtom';

const LogoButton = () => {
  const isMyPage = useLocation().pathname.startsWith('/mypage/');
  const isMain = useLocation().pathname === '/';
  const setIsModal = useSetRecoilState(homeModalAtom);
  const nextPath = isMyPage ? '/' : '#';

  const handleClick = () => {
    if (isMain) {
      window.scrollTo(0, 0);
    }
    if (!isMain) setIsModal(true);
  };

  return (
    <LogoButtonBox to={nextPath} onClick={handleClick}>
      TOT
    </LogoButtonBox>
  );
};

export default LogoButton;

const LogoButtonBox = styled(Link)`
  float: left;

  width: ${(props) => props.theme.width.leftNavWidth};
  height: ${(props) => props.theme.height.topNavHeight};

  background-color: ${(props) => props.theme.colors.commonNavBgColor};

  font-size: 25px;
  font-family: 'Pretendard-Regular';
  color: ${(props) => props.theme.colors.mainColor};
  text-align: center;
  text-decoration: none;

  ${media.mobile`
    padding: 20px 0 20px 0;
    font-size: 40px;
    font-family: 'Pretendard-Medium';
  `}
`;
