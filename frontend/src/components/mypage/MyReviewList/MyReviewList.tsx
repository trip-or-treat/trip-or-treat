import { useEffect, useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import styled from 'styled-components';

import { loginApi } from 'src/api';
import { END_POINTS } from 'src/constants/api';

import PlaceModal from 'src/components/PlaceModal';
import AlertModal from 'src/components/AlertModal';

import { ReactComponent as StarFilled } from '../../../assets/svgs/starFilled.svg';
import { ReactComponent as StarEmpty } from '../../../assets/svgs/starEmpty.svg';
import { ReactComponent as DeleteIcon } from '../../../assets/svgs/delete.svg';

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
  const [isEditClick, setEditClick] = useState(false);
  const [isModal, setModal] = useState(false);
  const [isDelete, setDelete] = useState(false);

  const handlerEditClick = () => {
    setEditClick(!isEditClick);
  };

  useEffect(() => {
    if (isEditClick) {
      localStorage.setItem('myReview', JSON.stringify(myReview));
      setModal(true);
    } else {
      localStorage.removeItem('myReview');
      setModal(false);
    }
  }, [isEditClick]);

  const handlerDeleteClick = () => {
    setDelete(!isDelete);
  };

  const queryClient = useQueryClient();
  const deleteReviewMutation = useMutation({
    mutationFn: () => loginApi.delete(`${END_POINTS.REVIEWS}/${myReview.id}`),
    onSuccess: () => {
      setDelete(!isDelete);
      return queryClient.invalidateQueries({
        queryKey: ['reviews'],
      });
    },
    onError: () => alert('삭제를 실패했습니다.'),
  });

  const onDelete = () => {
    deleteReviewMutation.mutate();
  };

  return (
    <ReviewContainer>
      <Index>{index}</Index>
      <ReviewBox onClick={handlerEditClick}>
        <Review>{myReview.content}</Review>
      </ReviewBox>
      <PlaceBox>
        <Place>{myReview.placeName}</Place>
      </PlaceBox>
      <Score>{renderStars(myReview.score)}</Score>
      <CreatedAt>{myReview.createdDate}</CreatedAt>
      <Delete onClick={handlerDeleteClick}>
        <DeleteIcon />
      </Delete>
      {isModal && <PlaceModal onClose={handlerEditClick} />}
      {isDelete && (
        <AlertModal
          onButtonText="삭제"
          offButtonText="닫기"
          onClick={onDelete}
          onClose={handlerDeleteClick}
        >
          정말 삭제하시겠어요?
        </AlertModal>
      )}
    </ReviewContainer>
  );
};

export default MyReviewList;

const ReviewContainer = styled.div`
  ${CommonContainer}

  justify-content: space-between;

  border-bottom: solid 1px ${(props) => props.theme.colors.lightGrey};

  svg {
    cursor: pointer;

    &:hover {
      fill: ${(props) => props.theme.colors.mainColor};
    }
  }
`;

const ReviewBox = styled.div`
  display: flex;
  align-items: center;

  width: 230px;
  height: 40px;

  cursor: pointer;
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

const Delete = styled.button`
  border: none;
  background-color: transparent;
`;
