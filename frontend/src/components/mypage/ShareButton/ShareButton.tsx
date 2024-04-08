import styled from 'styled-components';
import { useState } from 'react';

import { ReactComponent as Share } from '../../../assets/svgs/share.svg';
import { ReactComponent as Copy } from '../../../assets/svgs/copy.svg';

interface Props {
  code: string;
}

const ShareButton = ({ code }: Props) => {
  const [isClicked, setIsClicked] = useState(false);
  const SHARE_URL = `https://triportreat.site/plans/share/${code}`;

  const handleClick = () => {
    if (!SHARE_URL) return;
    navigator.clipboard.writeText(SHARE_URL);
    alert('공유 링크가 클립보드에 복사되었습니다.');
    setIsClicked(false);
  };

  return (
    <Wrapper>
      <Button onClick={() => setIsClicked((prev) => !prev)}>
        <Share />
      </Button>
      <TooltipBox $isClicked={isClicked}>
        <CodeBox>{SHARE_URL}</CodeBox>
        <CopyBox onClick={handleClick}>
          <Copy />
        </CopyBox>
      </TooltipBox>
    </Wrapper>
  );
};

export default ShareButton;

const Wrapper = styled.div`
  position: absolute;
  width: 20px;
  height: 35px;
  right: -25px;
  top: 1px;
  z-index: 9;
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

const TooltipBox = styled.div<{ $isClicked: boolean }>`
  display: ${(props) => (props.$isClicked ? 'flex' : 'none')};
  align-items: center;
  position: absolute;
  top: 0px;
  left: 50px;

  width: 400px;
  height: 30px;

  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 5px;
`;

const CodeBox = styled.div`
  overflow-x: scroll;
  display: flex;
  align-items: center;

  width: 90%;
  height: 100%;
  padding: 0px 5px;
  border-radius: 5px 0px 0px 5px;

  background-color: #f3f3f3;

  white-space: nowrap;
  scrollbar-width: none;
`;

const CopyBox = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 100%;
  width: 15%;

  border: none;
  border-radius: 0px 5px 5px 0px;
  border-left: 1px solid rgba(0, 0, 0, 0.3);
  cursor: pointer;

  svg {
    width: 20px;
    height: 20px;
  }
`;
