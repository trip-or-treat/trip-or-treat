import { useState } from 'react';
import { styled } from 'styled-components';

import { ReactComponent as StarFilled } from 'src/assets/svgs/starFilled.svg';
import { ReactComponent as Pencil } from 'src/assets/svgs/pencil.svg';

import ReviewInput from './ReviewInput';
import Reviews from './Reviews';

const PlaceReview = ({ currentId }: { currentId: number }) => {
  const [isReviewInput, setReviewInput] = useState(false);
  const [isReviews, setReviews] = useState(true);
  const prevPage = localStorage.getItem('prevPage');

  const onlyReviews = () => {
    const isOnly = prevPage?.split('/')[1] === 'place';
    return isOnly;
  };

  console.log(onlyReviews());

  onlyReviews();
  const onReviewInput = () => {
    setReviewInput(true);
    setReviews(false);
  };

  const onReviews = () => {
    setReviews(true);
    setReviewInput(false);
  };

  return (
    <Wrapper>
      {onlyReviews() ? (
        <Nav />
      ) : (
        <Nav>
          <Title
            onClick={onReviewInput}
            style={isReviewInput ? { borderBottom: `1px solid #d9d9d9` } : {}}
          >
            리뷰 작성 <Pencil />
          </Title>
          <Title onClick={onReviews} style={isReviews ? { borderBottom: '1px solid #d9d9d9' } : {}}>
            리뷰 <StarFilled />
          </Title>
        </Nav>
      )}
      {isReviewInput && <ReviewInput placeId={currentId} />}
      {isReviews && <Reviews placeId={currentId} />}
    </Wrapper>
  );
};

export default PlaceReview;

const Wrapper = styled.div`
  position: relative;
  left: 20px;

  width: 489px;
  height: 580px;
`;

const Nav = styled.div`
  display: flex;
  position: relative;
  justify-content: space-around;
  align-items: center;

  height: 10%;
  margin-top: 15px;
`;

const Title = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 50%;
  padding: 10px;

  border: none;
  background-color: transparent;

  text-align: center;
  font-size: 20px;
  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-SemiBold';

  outline: none;
  cursor: pointer;

  > * {
    margin-left: 10px;
  }
`;
