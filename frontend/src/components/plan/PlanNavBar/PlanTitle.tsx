import styled from 'styled-components';
import { useRecoilState } from 'recoil';
import { ChangeEvent, useRef, useState } from 'react';

import { planTitleAtom } from 'src/atoms/plan';
import ShareButton from 'src/components/mypage/ShareButton';
import { useMatch } from 'react-router-dom';
import { ReactComponent as Pen } from '../../../assets/svgs/pen.svg';

interface Props {
  code?: string;
  type?: 'MODAL';
}

const PlanTitle = ({ type, code }: Props) => {
  const titleRef = useRef<HTMLInputElement>(null);
  const [isChangingTitle, setIsChangingTitle] = useState(false);
  const [title, setTitle] = useRecoilState(planTitleAtom);
  const planMatch = useMatch('/plans');
  const planDetailMatch = useMatch('/mypage/plans/:planId');
  const sharedMatch = useMatch('/plans/share/:code');

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
      {(planDetailMatch || sharedMatch) && type !== 'MODAL' && <Title>{title}</Title>}
      {planDetailMatch && !type && <ShareButton code={code ?? ''} />}

      {(planMatch || type === 'MODAL') && (
        <>
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
        </>
      )}
    </Wrapper>
  );
};

export default PlanTitle;

const Wrapper = styled.div`
  position: relative;
  height: inherit;

  display: flex;
  justify-content: center;
  align-items: center;
`;

const Title = styled.div`
  width: 250px;
  height: inherit;

  color: ${(props) => props.theme.colors.blackFont};
  font-size: 30px;
  font-family: 'Pretendard-SemiBold';
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
    padding: 5px 0px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.3);
  }

  &::placeholder {
    color: ${(props) => props.theme.colors.darkGrey};
  }
`;

const CommonButton = styled.button`
  position: absolute;
  right: -50px;

  width: 50px;
  height: 40px;

  border: none;
  cursor: pointer;
`;

const ChangeButton = styled(CommonButton)`
  background-color: inherit;

  svg {
    width: 30px;
    height: 25px;
    fill: ${(props) => props.theme.colors.blackFont};

    &:hover {
      fill: ${(props) => props.theme.colors.mainColor};
    }
  }
`;

const ChangeEndButton = styled(CommonButton)`
  width: 40px;
  height: 30px;

  border-radius: 7px;
  background-color: ${(props) => props.theme.colors.mainColor};
  color: ${(props) => props.theme.colors.whiteFont};
`;
