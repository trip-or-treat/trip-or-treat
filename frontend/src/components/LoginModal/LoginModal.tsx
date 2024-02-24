import styled from 'styled-components';
import theme from 'src/styles/theme';
import LinkButton from '../@common/Modal/LinkButton';
import CloseButton from '../@common/Modal/CloseButton';

interface Props {
  onClose: () => void;
}

const LoginModal = ({ onClose }: Props) => {
  return (
    <Wrapper>
      <StyledModalLayout>
        <StyledModalText>저장하려면 로그인이 필요해요!</StyledModalText>
        <StyledButtonInner>
          <LinkButton path="/login" color={theme.colors.mainColor}>
            로그인
          </LinkButton>
          <CloseButton onClick={onClose} color={theme.colors.darkGrey}>
            닫기
          </CloseButton>
        </StyledButtonInner>
      </StyledModalLayout>
    </Wrapper>
  );
};

export default LoginModal;

export const Wrapper = styled.div`
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

export const StyledModalLayout = styled.div`
  display: flex;
  flex-flow: column;

  width: 356px;
  height: 179px;

  border: none;
  border-radius: 10px;

  background-color: ${(props) => props.theme.colors.whiteFont};

  box-shadow: 0px 2px 2px 0px darkgray;
`;

export const StyledModalText = styled.h1`
  display: flex;
  justify-content: center;
  align-self: center;
  align-items: center;

  width: 272px;
  height: 109px;

  font-size: 15px;
  line-height: 1.5;
`;

export const StyledButtonInner = styled.div`
  display: flex;
  justify-content: center;
`;
