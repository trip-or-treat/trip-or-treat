import { useEffect, useState } from 'react';
import styled from 'styled-components';

import PlaceModal from 'src/components/PlaceModal';
import AlertModal from 'src/components/AlertModal';

import { deleteReviewFetcher } from 'src/api/review';

import { ReactComponent as StarFilled } from '../../../assets/svgs/starFilled.svg';
import { ReactComponent as StarEmpty } from '../../../assets/svgs/starEmpty.svg';
import { ReactComponent as EditIcon } from '../../../assets/svgs/edit.svg';
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
  const [isDelte, setDelte] = useState(false);

  const handlerEditClick = () => {
    setEditClick(!isEditClick);
  };

  // useEffect만 사용한다면 비동기이기 때문에 modal의 상태가 변경되었을 때 바로 modal이
  // 데이터가 셋팅되지 않는 상태에서 열리기 때문에 에러발생
  // isisEditClick가 변경될 때 마다 실행되기때문에 setModal(!isModal)을 한다면 켰다가 꺼지면서
  // isModal의 값이 동일해지기 때문에 modal이 안열림

  useEffect(() => {
    if (isEditClick) {
      localStorage.setItem('myReview', JSON.stringify(myReview));
      setModal(true);
    } else {
      localStorage.removeItem('myReview');
      setModal(false);
    }
  }, [isEditClick]);

  const handlerDelteClick = () => {
    setDelte(!isDelte);
  };

  const onDelte = async () => {
    await deleteReviewFetcher(myReview.id);
    setDelte(!isDelte);
    window.location.href = '/mypage/myReview';
  };

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
      <div>
        <Edit onClick={handlerEditClick}>
          <EditIcon />
        </Edit>
        <Edit onClick={handlerDelteClick}>
          <DeleteIcon />
        </Edit>
      </div>
      {isModal && <PlaceModal onClose={handlerEditClick} />}
      {isDelte && (
        <AlertModal
          onButtonText="삭제"
          offButtonText="닫기"
          onClick={onDelte}
          onClose={handlerDelteClick}
        >
          정말 삭제하시겠습니까?
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

const Edit = styled.button`
  padding: 2px;

  border: none;
  background-color: transparent;
`;
