import styled from 'styled-components';
import React from 'react';
import theme from 'src/styles/theme';

import ModalOverlay from '../common/modal/ModalOverlay';
import LinkButton from '../common/modal/LinkButton';
import CloseButton from '../common/modal/CloseButton';

interface Props {
  onClose: () => void;
  children: React.ReactNode;
  onButtonText: string;
  offButtonText: string;
  path: string;
}

const LoginModal = ({ children, onButtonText, offButtonText, path, onClose }: Props) => {
  return (
    <ModalOverlay>
      <StyledModalLayout>
        <StyledModalText>{children}</StyledModalText>
        <StyledButtonInner>
          <LinkButton path={path} color={theme.colors.mainColor}>
            {onButtonText}
          </LinkButton>
          <CloseButton onClick={onClose} color={theme.colors.darkGrey}>
            {offButtonText}
          </CloseButton>
        </StyledButtonInner>
      </StyledModalLayout>
    </ModalOverlay>
  );
};

export default LoginModal;

export const StyledModalLayout = styled.div`
  display: flex;
  flex-flow: column;

  width: 356px;
  height: 179px;

  border: none;
  border-radius: 10px;

  background-color: ${(props) => props.theme.colors.whiteFont};

  box-shadow: 2px 2px 3px 0px ${(props) => props.theme.colors.darkGrey};
`;

export const StyledModalText = styled.h1`
  display: flex;
  justify-content: center;
  align-self: center;
  align-items: center;

  width: 272px;
  height: 109px;
  margin: 15px 15px 0px 15px;

  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-SemiBold';
  font-size: 18px;
  line-height: 1.5;
`;

export const StyledButtonInner = styled.div`
  display: flex;
  justify-content: center;
`;
