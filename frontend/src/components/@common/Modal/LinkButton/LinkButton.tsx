import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

interface Props {
  path: string;
  color: string;
  children: React.ReactNode;
}

const LinkButton = ({ path, color, children }: Props) => {
  return (
    <StyledLinkButton to={path} color={color}>
      {children}
    </StyledLinkButton>
  );
};

export default LinkButton;

export const StyledButton = `
  width: 68px;
  height: 31.8px;
  margin: 18px;

  border: none;
  border-radius: 5px;
  font-size: 14px;

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
