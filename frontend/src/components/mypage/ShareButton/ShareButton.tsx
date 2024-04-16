import styled from 'styled-components';

import { ReactComponent as Share } from '../../../assets/svgs/share.svg';

interface Props {
  code: string;
}

const ShareButton = ({ code }: Props) => {
  const SHARE_URL = `https://triportreat.site/plans/share/${code}`;

  const handleClick = () => {
    if (!SHARE_URL) return;
    navigator.clipboard.writeText(SHARE_URL);
    alert('공유 링크가 클립보드에 복사되었습니다.');
  };

  return (
    <Wrapper>
      <Button onClick={handleClick}>
        <Share />
      </Button>
    </Wrapper>
  );
};

export default ShareButton;

const Wrapper = styled.div`
  position: absolute;
  right: -25px;
  top: 1px;
  z-index: 9;

  width: 20px;
  height: 35px;
`;

const Button = styled.button`
  border: none;
  background-color: inherit;
  cursor: pointer;

  svg {
    width: 25px;
    height: 25px;
    fill: ${(props) => props.theme.colors.lightGrey};

    &:hover {
      fill: ${(props) => props.theme.colors.hoverColor};
    }
  }
`;
