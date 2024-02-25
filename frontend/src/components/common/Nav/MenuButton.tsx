import styled from 'styled-components';
import { ReactNode } from 'react';
import { Link } from 'react-router-dom';

interface ButtonTextProps {
  path: string;
  children: ReactNode;
}

const MenuButton = ({ path, children }: ButtonTextProps) => {
  return <MenuButtonBox to={path}>{children}</MenuButtonBox>;
};

export default MenuButton;

const MenuButtonBox = styled(Link)`
  float: right;

  padding: 0px 35px;

  background-color: ${(props) => props.theme.colors.commonNavBgColor};

  font-size: 20px;
  font-family: 'Pretendard-Regular';
  color: ${(props) => props.theme.colors.blackFont};
  text-decoration: none;
`;
