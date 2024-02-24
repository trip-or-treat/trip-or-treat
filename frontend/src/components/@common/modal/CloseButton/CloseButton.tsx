import React from 'react';
import styled from 'styled-components';
import { StyledButton } from '../LinkButton/LinkButton';

interface Props {
  onClick: () => void; // 후에 모달을 사용하는 공간에서 open, close 함수를 만들어서 누르면 꺼지게 동작
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
