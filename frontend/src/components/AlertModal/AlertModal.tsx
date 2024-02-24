import theme from 'src/styles/theme';
import LinkButton from '../@common/Modal/LinkButton';
import CloseButton from '../@common/Modal/CloseButton';
import {
  Wrapper,
  StyledModalLayout,
  StyledModalText,
  StyledButtonInner,
} from '../LoginModal/LoginModal';

interface Props {
  onClose: () => void;
}

const AlertModal = ({ onClose }: Props) => {
  return (
    <Wrapper>
      <StyledModalLayout>
        <StyledModalText>
          계획 생성을 중단하시겠습니까?
          <br />
          변경사항은 저장되지 않습니다.
        </StyledModalText>
        <StyledButtonInner>
          <CloseButton onClick={onClose} color={theme.colors.mainColor}>
            계속하기
          </CloseButton>
          <LinkButton path="/home" color={theme.colors.darkGrey}>
            홈으로
          </LinkButton>
        </StyledButtonInner>
      </StyledModalLayout>
    </Wrapper>
  );
};

export default AlertModal;
