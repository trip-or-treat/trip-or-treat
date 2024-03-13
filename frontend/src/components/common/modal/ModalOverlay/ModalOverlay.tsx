import React from 'react';
import styled from 'styled-components';

interface Props {
  children: React.ReactNode;
}

const ModalOverlay = ({ children }: Props) => {
  const onBodyOverflow = () => {
    document.body.style.overflowY = 'hidden';
  };

  return (
    <>
      {onBodyOverflow()}
      <StyledModalOverlay>{children}</StyledModalOverlay>
    </>
  );
};

export default ModalOverlay;

const StyledModalOverlay = styled.div`
  display: flex;
  justify-content: center;
  position: fixed;
  top: 0;
  left: 0;
  align-items: center;
  z-index: 999;

  width: 100%;
  height: 100%;

  background-color: rgba(0, 0, 0, 0.4);
`;
