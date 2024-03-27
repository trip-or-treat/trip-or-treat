import { useState, ChangeEvent } from 'react';
import { styled } from 'styled-components';

import { ReactComponent as StarFilled } from 'src/assets/svgs/starFilled.svg';
import { ReactComponent as StarEmpty } from 'src/assets/svgs/starEmpty.svg';
import { ReactComponent as HoneyPot } from 'src/assets/svgs/honeyPot.svg';
import { ReactComponent as SpeechBubble } from 'src/assets/svgs/speechBubble.svg';

const ReviewInput = ({ placeId }: { placeId: number }) => {
  const [content, setContent] = useState('');
  const [tip, setTip] = useState('');
  const [score, setScore] = useState(0);
  const [hoverIndex, setHoverIndex] = useState(-1);
  const [isSubmit, setSubmit] = useState(false);
  const scoreArr = Array.from({ length: 5 }).map((_, index) => `star${index}`);

  const onSubmit = () => {
    if (score === 0 || score === -1) {
      setScore(-1);
    } else {
      setSubmit(true);
      setContent('');
      setTip('');
      setScore(0);
    }
  };

  const handleContent = (event: ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length <= 200) {
      setContent(event.target.value);
    }
  };

  const handleTip = (event: ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length <= 20) {
      setTip(event.target.value);
    }
  };

  const handleStarClick = (index: number) => {
    setScore(index + 1);
  };

  const handleOnStar = (index: number) => {
    setHoverIndex(index);
  };

  const handleCloseStar = () => {
    setHoverIndex(-1);
  };

  const hoverFill = (index: number) => {
    return hoverIndex >= 0 && index <= hoverIndex ? 'hoverRange' : '';
  };

  return (
    <InputInner>
      <SemiTitle>
        <SpeechBubble />
        리뷰
        <Score>
          {scoreArr.map((key, index) => (
            <Svg
              key={key}
              className={hoverFill(index)}
              onClick={() => handleStarClick(index)}
              onMouseEnter={() => handleOnStar(index)}
              onMouseLeave={handleCloseStar}
              $hoverIndex={hoverIndex}
            >
              {index < score ? <StarFilled /> : <StarEmpty />}
            </Svg>
          ))}
        </Score>
        {score === -1 && <RatingEmpty>별점을 입력해주세요.</RatingEmpty>}
      </SemiTitle>
      <Content value={content} placeholder="이 장소는 어떠셨나요?" onChange={handleContent} />
      <SemiTitle>
        <HoneyPot />
        꿀팁
      </SemiTitle>
      <Tip value={tip} placeholder="꿀팁이 있다면 알려주세요! (20자)" onChange={handleTip} />
      {!isSubmit ? (
        <InputSubmit onClick={onSubmit}>리뷰 등록</InputSubmit>
      ) : (
        <SubmitCompleted>등록 완료</SubmitCompleted>
      )}
    </InputInner>
  );
};

export default ReviewInput;

const InputInner = styled.div`
  height: 80%;
  padding: 20px;

  > * {
    color: ${(props) => props.theme.colors.blackFont};
    font-family: 'Pretendard-Regular';
    font-size: 16px;
  }
`;

const SemiTitle = styled.h3`
  display: flex;

  margin-bottom: 10px;

  font-size: 20px;
`;

const Content = styled.textarea`
  width: 100%;
  height: 45%;
  margin-bottom: 30px;

  border-radius: 10px;
  border: solid;
  border-color: ${(props) => props.theme.colors.lightGrey};

  line-height: 1.5;
  text-indent: 10px;

  outline: none;
  resize: none;
`;

const RatingEmpty = styled.div`
  margin-left: 10px;

  color: ${(props) => props.theme.colors.mainColor};
  font-family: 'Pretendard-SemiBold';
`;

const Tip = styled.textarea`
  width: 100%;
  height: 25%;

  border-radius: 10px;
  border: solid;
  border-color: ${(props) => props.theme.colors.lightGrey};

  outline: none;
  resize: none;

  line-height: 1.5;
  text-indent: 10px;
`;

const InputSubmit = styled.button`
  height: 10%;
  width: 101%;
  margin-top: 15px;

  border: none;
  border-radius: 10px;
  background-color: ${(props) => props.theme.colors.yellow};
  color: ${(props) => props.theme.colors.whiteFont};

  cursor: pointer;
`;

const SubmitCompleted = styled(InputSubmit)`
  background-color: ${(props) => props.theme.colors.darkGrey};
  color: ${(props) => props.theme.colors.whiteFont};

  pointer-events: none;
`;

const Score = styled.div`
  display: flex;
  position: relative;

  bottom: 4px;
  margin-left: 15px;
`;

const Svg = styled.button<{ $hoverIndex: number }>`
  position: relative;
  padding: 0;

  border: none;
  background-color: transparent;

  outline: none;
  cursor: pointer;

  &.hoverRange path {
    fill: #ffd233;
  }
`;
