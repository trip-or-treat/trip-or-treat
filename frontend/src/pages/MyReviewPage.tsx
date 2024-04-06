import styled from 'styled-components';
import { useEffect, useState } from 'react';

import EmptyBox from 'src/components/common/EmptyBox';
import MyPageTitle from 'src/components/mypage/MyPageTitle';
import ReviewListCategory from 'src/components/mypage/ReviewListCategory';
import MyReviewList from 'src/components/mypage/MyReviewList';
import Paging from 'src/components/mypage/Pagination/Pagination';

import { useReviews } from 'src/hooks/api/useMyReviews';
import { MyReview } from 'src/@types/api/myReview';

import FailDataPage from './FailDataPage';

interface Data {
  data: {
    data: MyReviews;
  };
  isLoading: boolean;
  isError: boolean;
}

interface MyReviews {
  contents: {
    id: number;
    content: string;
    tip: string;
    placeId: number;
    placeName: string;
    score: number;
    createdDate: string;
  }[];
  totalElements: number;
  totalPages: number;
}

const MyReviewPage = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [currentPost, setCurrentPost] = useState<MyReview[]>([]); // 게시판 목록에 보여줄 게시글

  const { data: myReviews, isLoading, isError }: Data = useReviews({ pageParam: currentPage });

  let totalElements = myReviews?.data.totalElements;
  if (myReviews?.data.totalElements === 0) totalElements = 1;

  useEffect(() => {
    if (!isLoading) setCurrentPost(myReviews.data.contents);
  }, [myReviews]);

  if (currentPost.length === 0 && currentPage !== 1) setCurrentPage(myReviews?.data.totalPages);

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  return (
    <Wrapper>
      <MyPageTitle>내 리뷰 목록</MyPageTitle>
      <ReviewListCategory />
      {isError && <FailDataPage />}
      <ListContainer>
        {currentPost.length === 0 && <EmptyBox>작성한 리뷰가 없습니다.</EmptyBox>}
        {currentPost?.map((data, index) => {
          return <MyReviewList key={data.id} data={data} index={index + 1} />;
        })}
      </ListContainer>
      <Paging
        currentPage={currentPage}
        totalElements={totalElements}
        setCurrentPage={handlePageChange}
      />
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
