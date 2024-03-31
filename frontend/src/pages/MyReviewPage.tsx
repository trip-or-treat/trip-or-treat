import styled from 'styled-components';
import { useEffect, useState } from 'react';

import MyPageTitle from 'src/components/mypage/MyPageTitle';
import ReviewListCategory from 'src/components/mypage/ReviewListCategory';
import MyReviewList from 'src/components/mypage/MyReviewList';
import Paging from 'src/components/mypage/Pagination/Pagination';

import { useReviews } from 'src/hooks/api/useMyReviews';
import { MyReview } from 'src/@types/api/myReview';

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
  const [currentPage, setCurrentPage] = useState(1);
  const [currentPost, setCurrentPost] = useState<MyReview[]>([]); // 게시판 목록에 보여줄 게시글

  const { data: myReviews }: MyReviews = useReviews({ pageParam: currentPage });

  const totalElements = myReviews?.data.totalElements;

  useEffect(() => {
    setCurrentPost(myReviews?.data.contents);
  }, [myReviews]);

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  return (
    <Wrapper>
      <MyPageTitle>내 리뷰 목록</MyPageTitle>
      <ReviewListCategory />
      <ListContainer>
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
