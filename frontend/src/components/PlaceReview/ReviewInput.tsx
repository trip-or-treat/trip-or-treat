import { useState, ChangeEvent } from 'react';
import { styled } from 'styled-components';

import { ReactComponent as StarFilled } from 'src/assets/svgs/starFilled.svg';
import { ReactComponent as StarEmpty } from 'src/assets/svgs/starEmpty.svg';
import { ReactComponent as HoneyPot } from 'src/assets/svgs/honeyPot.svg';
import { ReactComponent as SpeechBubble } from 'src/assets/svgs/speechBubble.svg';

const ReviewInput = () => {
  const [review, setReview] = useState('');
  const [tip, setTip] = useState('');
  const [rating, setRating] = useState(0);
  const [hoverIndex, setHoverIndex] = useState(-1);
  const scoreArr = Array.from({ length: 5 }).map((_, index) => `star${index}`);

  const onInput = () => {
    if (rating === 0 || rating === -1) {
      setRating(-1);
    } else {
      setReview('');
      setTip('');
      setRating(0);
    }
  };

  const handleReview = (event: ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length <= 200) {
      setReview(event.target.value);
    }
  };

  const handleTip = (event: ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length <= 20) {
      setTip(event.target.value);
    }
  };

  const handleStarClick = (index: number) => {
    setRating(index + 1);
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
              {index < rating ? <StarFilled /> : <StarEmpty />}
            </Svg>
          ))}
        </Score>
        {rating === -1 && <RatingEmpty>별점을 입력해주세요.</RatingEmpty>}
      </SemiTitle>
      <Review value={review} placeholder="이 장소는 어떠셨나요?" onChange={handleReview} />
      <SemiTitle>
        <HoneyPot />
        꿀팁
      </SemiTitle>
      <Tip value={tip} placeholder="꿀팁이 있다면 알려주세요! (20자)" onChange={handleTip} />
      <InputSubmit onClick={onInput}>리뷰 등록</InputSubmit>
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

const Review = styled.textarea`
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
