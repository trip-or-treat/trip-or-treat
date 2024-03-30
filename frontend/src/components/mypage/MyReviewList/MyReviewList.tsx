import { useEffect, useState } from 'react';
import styled from 'styled-components';

import { ReactComponent as StarFilled } from '../../../assets/svgs/starFilled.svg';
import { ReactComponent as StarEmpty } from '../../../assets/svgs/starEmpty.svg';
import { ReactComponent as EditIcon } from '../../../assets/svgs/edit.svg';
import { CommonContainer } from '../../../styles/CommonContainer';

interface MyReviews {
  data: {
    id: number;
    content: string;
    tip: string;
    placeId: number;
    placeName: string;
    score: number;
    createdDate: string;
  };
  index: number;
}

const renderStars = (score: number) => {
  const stars = [];
  for (let i = 1; i <= 5; i += 1) {
    if (i <= score) {
      stars.push(<StarFilled key={i} />);
    } else {
      stars.push(<StarEmpty key={i} />);
    }
  }
  return stars;
};

const MyReviewList = ({ data: myReview, index }: MyReviews) => {
  const [isModal, setModal] = useState(false);

  const modalHandler = () => {
    console.log(1);
    setModal(!isModal);
  };

  useEffect(() => {
    if (isModal) {
      localStorage.setItem('myReview', JSON.stringify(myReview));
    } else {
      localStorage.removeItem('myReview');
    }
  }, [isModal]);

  return (
    <ReviewContainer>
      <Index>{index}</Index>
      <ReviewBox>
        <Review>{myReview.content}</Review>
      </ReviewBox>
      <PlaceBox>
        <Place>{myReview.placeName}</Place>
      </PlaceBox>
      <Score>{renderStars(myReview.score)}</Score>
      <CreatedAt>{myReview.createdDate}</CreatedAt>
      <Edit onClick={modalHandler}>
        <EditIcon />
      </Edit>
    </ReviewContainer>
  );
};

export default MyReviewList;

const ReviewContainer = styled.div`
  ${CommonContainer}

  justify-content: space-between;

  border-bottom: solid 1px ${(props) => props.theme.colors.lightGrey};

  svg {
    width: 25px;
    cursor: pointer;

    &:hover {
      fill: ${(props) => props.theme.colors.mainColor};
    }
  }

  > * {
    color: ${(props) => props.theme.colors.blackFont};
  }
`;

const ReviewBox = styled.div`
  width: 230px;
`;

const PlaceBox = styled.div`
  width: 140px;
`;

const Index = styled.div`
  width: 35px;
  text-align: center;
`;

const Review = styled.div`
  display: block;

  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const Place = styled.div`
  display: block;

  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const Score = styled.div`
  width: 80px;

  font-size: 13px;
  text-align: center;

  svg {
    width: 16px;
    cursor: default;
  }
`;

const CreatedAt = styled.div`
  width: 75px;

  font-size: 12px;
  text-align: center;
`;

const Edit = styled.button``;
