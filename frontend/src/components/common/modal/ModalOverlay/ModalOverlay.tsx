import React from 'react';
import styled from 'styled-components';

interface Props {
  children: React.ReactNode;
}

const ModalOverlay = ({ children }: Props) => {
  return <StyledModalOverlay>{children}</StyledModalOverlay>;
};

export default ModalOverlay;

const StyledModalOverlay = styled.div`
  display: flex;
  justify-content: center;
  position: fixed;
  top: 0;
  left: 0;
  align-items: center;

  width: 100%;
  height: 100%;

  background-color: rgba(0, 0, 0, 0.1);
`;