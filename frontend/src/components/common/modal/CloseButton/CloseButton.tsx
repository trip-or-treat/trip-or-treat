import React from 'react';
import styled from 'styled-components';

import { StyledButton } from '../LinkButton/LinkButton';

interface Props {
  onClick: () => void;
  color: string;
  children: React.ReactNode;
}

const CloseButton = ({ onClick, color, children }: Props) => {
  return (
    <StyledCloseButton onClick={onClick} color={color}>
      {children}
    </StyledCloseButton>
  );
};

export default CloseButton;

const StyledCloseButton = styled.button<{ color: string }>`
  ${StyledButton}

  background-color: ${(props) => props.color};
  color: ${(props) => props.theme.colors.whiteFont};
`;
