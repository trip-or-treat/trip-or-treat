import styled from 'styled-components';

import { ReactComponent as StarFilled } from '../../../assets/svgs/starFilled.svg';
import { ReactComponent as StarEmpty } from '../../../assets/svgs/starEmpty.svg';
import { ReactComponent as EditIcon } from '../../../assets/svgs/edit.svg';
import { CommonContainer } from '../CommonContainer';

interface Props {
  idx: number;
  review: string;
  place: string;
  score: number;
  createdAt: string;
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

const MyReviewList = ({ idx, review, place, score, createdAt }: Props) => {
  return (
    <ReviewContainer>
      <Index>{idx}</Index>
      <ReviewBox>
        <Review>{review}</Review>
      </ReviewBox>
      <PlaceBox>
        <Place>{place}</Place>
      </PlaceBox>
      <Score>{renderStars(score)}</Score>
      <CreatedAt>{createdAt}</CreatedAt>
      <EditIcon />
    </ReviewContainer>
  );
};

export default MyReviewList;

const ReviewContainer = styled.div`
  ${CommonContainer}

  justify-content: space-between;

  border-radius: 35px;
  box-shadow: 0px 2px 4px 0px ${(props) => props.theme.colors.lightGrey};

  svg {
    width: 25px;
    cursor: pointer;
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
