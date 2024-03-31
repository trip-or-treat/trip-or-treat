import styled from 'styled-components';

import MyPageTitle from 'src/components/mypage/MyPageTitle';
import ReviewListCategory from 'src/components/mypage/ReviewListCategory';
import MyReviewList from 'src/components/mypage/MyReviewList';
import { useReviews } from 'src/hooks/api/useMyReviews';
import { useState } from 'react';
import Paging from 'src/components/mypage/Pagination/Pagination';

interface MyReviews {
  data: {
    data: {
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
    };
    index: number;
  };
}

const MyReviewPage = () => {
  const { data: myReviews }: MyReviews = useReviews();

  const [currentPage, setCurrentPage] = useState(1);
  const totalElements = myReviews?.data.totalElements;

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  return (
    <Wrapper>
      <MyPageTitle>내 리뷰 목록</MyPageTitle>
      <ReviewListCategory />
      <ListContainer>
        {myReviews?.data.contents.map((data, index) => {
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
