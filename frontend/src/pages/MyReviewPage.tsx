import styled from 'styled-components';

import MyPageTitle from 'src/components/mypage/MyPageTitle';
import ReviewListCategory from 'src/components/mypage/ReviewListCategory';
import MyReviewList from 'src/components/mypage/MyReviewList';
import { useEffect, useState } from 'react';
import Paging from 'src/components/mypage/Pagination/Pagination';
import { MyReview } from 'src/@types/api/myReview';

const MY_REVIEW_DATA = [
  {
    id: 1,
    content: '사람이 너무너무 많아요 ',
    placeName: '망원 한강공원ㅇㅇㅇㅇㅇㅇㅇㅇ',
    score: 5,
    createdDate: '2024-03-14',
  },
  {
    id: 2,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 5,
    createdDate: '2024-03-14',
  },
  {
    id: 3,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 2,
    createdDate: '2024-03-14',
  },
  {
    id: 4,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 1,
    createdDate: '2024-03-14',
  },
  {
    id: 5,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 4,
    createdDate: '2024-03-14',
  },
  {
    id: 6,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 3,
    createdDate: '2024-03-14',
  },
  {
    id: 7,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 2,
    createdDate: '2024-03-14',
  },
  {
    id: 8,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 1,
    createdDate: '2024-03-14',
  },
  {
    id: 9,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 4,
    createdDate: '2024-03-14',
  },
  {
    id: 10,
    content: '사람이 너무너무 많아요 근데 볼거리가 진짜 많아서 추천해요!',
    placeName: '망원 한강공원',
    score: 3,
    createdDate: '2024-03-14',
  },
];

const MyReviewPage = () => {
  // const [planList, setPlanList] = useState<Plan[]>([]); //api로 받아온 데이터
  const [currentPost, setCurrentPost] = useState<MyReview[]>([]); // 게시판 목록에 보여줄 게시글
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지 번호

  const postPerPage = 10;
  const indexOfLastPost = currentPage * postPerPage; // 10,20,30...
  const indexOfFirstPost = indexOfLastPost - postPerPage;

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  console.log(currentPage);
  console.log(currentPost);

  // useEffect(() => {
  //   //api 요청해서 계획목록 불러오기. axios.get('baseurl/plans')
  //   .then((response) => {
  //     setPlanList([...response.data].reverse())
  //   })

  //   .catch(function(error) {
  //     console.log(error)
  //   })
  // },[])

  useEffect(() => {
    setCurrentPost(MY_REVIEW_DATA.slice(indexOfFirstPost, indexOfLastPost));
  }, [MY_REVIEW_DATA, currentPage]);

  return (
    <Wrapper>
      <MyPageTitle>내 리뷰 목록</MyPageTitle>
      <ReviewListCategory />
      <ListContainer>
        {MY_REVIEW_DATA.map((data, idx) => {
          return (
            <MyReviewList
              key={data.id}
              idx={idx + 1}
              review={data.content}
              place={data.placeName}
              score={data.score}
              createdDate={data.createdDate}
            />
          );
        })}
      </ListContainer>
      <Paging currentPage={currentPage} totalElements={10} setCurrentPage={handlePageChange} />
    </Wrapper>
  );
};

export default MyReviewPage;

const Wrapper = styled.div`
  width: 800px;
`;

const ListContainer = styled.div`
  max-height: 60vh;
  min-height: 60vh;
  overflow-y: auto;
`;
