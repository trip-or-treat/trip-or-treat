import styled from 'styled-components';

import MyPageTitle from 'src/components/mypage/MyPageTitle';
import ReviewListCategory from 'src/components/mypage/ReviewListCategory';
import MyReviewList from 'src/components/mypage/MyReviewList';

const MY_REVIEW_DATA = [
  {
    placeId: '1',
    review: '사람이 너무너무 많아요 ',
    place: '망원 한강공원ㅇㅇㅇㅇㅇㅇㅇㅇ',
    score: 5,
    createdAt: '2024-03-14',
  },
  {
    placeId: '2',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 5,
    createdAt: '2024-03-14',
  },
  {
    placeId: '3',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 2,
    createdAt: '2024-03-14',
  },
  {
    placeId: '4',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 1,
    createdAt: '2024-03-14',
  },
  {
    placeId: '5',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 4,
    createdAt: '2024-03-14',
  },
  {
    placeId: '6',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 3,
    createdAt: '2024-03-14',
  },
  {
    placeId: '7',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 2,
    createdAt: '2024-03-14',
  },
  {
    placeId: '8',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 1,
    createdAt: '2024-03-14',
  },
  {
    placeId: '9',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 4,
    createdAt: '2024-03-14',
  },
  {
    placeId: '10',
    review: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    place: '망원 한강공원',
    score: 3,
    createdAt: '2024-03-14',
  },
];

const MyReviewPage = () => {
  return (
    <Wrapper>
      <MyPageTitle>내 리뷰 목록</MyPageTitle>
      <ReviewListCategory />
      <ListContainer>
        {MY_REVIEW_DATA.map((data, idx) => {
          return (
            <MyReviewList
              key={data.placeId}
              idx={idx + 1}
              review={data.review}
              place={data.place}
              score={data.score}
              createdAt={data.createdAt}
            />
          );
        })}
      </ListContainer>
    </Wrapper>
  );
};

export default MyReviewPage;

const Wrapper = styled.div`
  width: 800px;
`;

const ListContainer = styled.div`
  max-height: 60vh;
  overflow-y: auto;
`;
