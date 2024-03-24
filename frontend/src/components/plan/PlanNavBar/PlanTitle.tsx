import styled from 'styled-components';
import { useRecoilState } from 'recoil';
import { ChangeEvent, useRef, useState } from 'react';

import { StyledButton } from 'src/components/common/modal/LinkButton/LinkButton';
import { planTitleAtom } from 'src/atoms/plan';
import { ReactComponent as Pen } from '../../../assets/svgs/pen.svg';

const PlanTitle = () => {
  const titleRef = useRef<HTMLInputElement>(null);
  const [isChangingTitle, setIsChangingTitle] = useState(false);
  const [title, setTitle] = useRecoilState(planTitleAtom);

  const handleTitleClick = () => {
    if (titleRef.current !== null) {
      titleRef.current.disabled = false;
      titleRef.current.focus();
    }
    setIsChangingTitle(true);
  };

  const handleTitleInput = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.value.length === 13) return;
    setTitle(e.target.value);
  };

  return (
    <Wrapper>
      <Input
        ref={titleRef}
        value={title}
        onChange={handleTitleInput}
        disabled={!isChangingTitle}
        maxLength={12}
        placeholder="여행자님의 여행계획"
      />

      {isChangingTitle ? (
        <ChangeEndButton type="button" onClick={() => setIsChangingTitle(false)}>
          수정
        </ChangeEndButton>
      ) : (
        <ChangeButton onClick={handleTitleClick}>
          <Pen />
        </ChangeButton>
      )}
    </Wrapper>
  );
};

export default PlanTitle;

const Wrapper = styled.div`
  position: fixed;
  left: 40%;
  height: 40px;
`;

const Input = styled.input`
  outline: none;

  width: 250px;

  border: none;
  background-color: inherit;

  color: ${(props) => props.theme.colors.blackFont};
  font-size: 30px;
  font-family: 'Pretendard-SemiBold';

  &:focus {
    border-bottom: 1px solid rgba(0, 0, 0, 0.3);
  }

  &::placeholder {
    color: ${(props) => props.theme.colors.darkGrey};
  }
`;

const ChangeButton = styled.button`
  border: none;
  background-color: inherit;
  cursor: pointer;

  svg {
    width: 50px;
    height: 20px;
    fill: ${(props) => props.theme.colors.blackFont};
  }
`;

const ChangeEndButton = styled.button`
  ${StyledButton}

  position: absolute;
  top: 4px;
  width: 50px;

  background-color: ${(props) => props.theme.colors.mainColor};
  color: ${(props) => props.theme.colors.whiteFont};

  cursor: pointer;
`;
