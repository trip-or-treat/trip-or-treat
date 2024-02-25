import styled from 'styled-components';

import LogoButton from './LogoButton';
import MenuButton from './MenuButton';

const CommonNav = () => {
  return (
    <CommonNavBox>
      <LogoButton />
      <MenuButton path="/">로그인</MenuButton>
      <MenuButton path="/">이용방법</MenuButton>
    </CommonNavBox>
  );
};

export default CommonNav;

const CommonNavBox = styled.div`
  display: block;

  height: ${(props) => props.theme.height.topNavHeight};

  background-color: ${(props) => props.theme.colors.commonNavColor};

  line-height: ${(props) => props.theme.height.topNavHeight};
`;
