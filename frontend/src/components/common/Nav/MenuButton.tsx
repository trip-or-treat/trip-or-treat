import styled from 'styled-components';
import { ReactNode } from 'react';
import { Link } from 'react-router-dom';

interface ButtonTextProps {
  path: string;
  children: ReactNode;
  onClick?: () => void;
}

const MenuButton = ({ path, children, onClick }: ButtonTextProps) => {
  return (
    <MenuButtonBox to={path} onClick={onClick}>
      {children}
    </MenuButtonBox>
  );
};

export default MenuButton;

const MenuButtonBox = styled(Link)`
  float: right;

  padding: 0px 25px;

  background-color: ${(props) => props.theme.colors.commonNavBgColor};

  font-size: 17px;
  font-family: 'Pretendard-Regular';
  color: ${(props) => props.theme.colors.blackFont};
  text-decoration: none;
`;
