import styled from 'styled-components';
import { Link } from 'react-router-dom';

const LogoButton = () => {
  return <LogoButtonBox to="/">TOT</LogoButtonBox>;
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
`;
