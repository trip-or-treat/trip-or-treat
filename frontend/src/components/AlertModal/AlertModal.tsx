import theme from 'src/styles/theme';
import ModalOverlay from '../common/modal/ModalOverlay';

import LinkButton from '../common/modal/LinkButton';
import CloseButton from '../common/modal/CloseButton';

import { StyledModalLayout, StyledModalText, StyledButtonInner } from '../LoginModal/LoginModal';

interface Props {
  onClose: () => void;
}

const AlertModal = ({ onClose }: Props) => {
  return (
    <ModalOverlay>
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
          <LinkButton path="/" color={theme.colors.darkGrey}>
            홈으로
          </LinkButton>
        </StyledButtonInner>
      </StyledModalLayout>
    </ModalOverlay>
  );
};

export default AlertModal;
