import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

interface Props {
  path?: string;
  color: string;
  children: React.ReactNode;
  onClick?: () => void;
}

const LinkButton = ({ path, color, children, onClick }: Props) => {
  return path ? (
    <StyledLinkButton to={path} color={color} onClick={onClick}>
      {children}
    </StyledLinkButton>
  ) : (
    <OptionalButton onClick={onClick}>{children}</OptionalButton>
  );
};

export default LinkButton;

export const StyledButton = `
  width: 68px;
  height: 31.8px;
  margin: 0px 15px 15px 15px;

  border: none;
  border-radius: 5px;
  font-size: 14px;
  font-family: 'Pretendard-SemiBold';

  text-decoration: none;
  cursor: pointer;
`;

const StyledLinkButton = styled(Link)<{ color: string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  ${StyledButton}

  background-color: ${(props) => props.color};
  color: ${(props) => props.theme.colors.whiteFont};
`;

const OptionalButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  ${StyledButton}

  background-color: ${(props) => props.theme.colors.mainColor};
  color: ${(props) => props.theme.colors.whiteFont};
`;
