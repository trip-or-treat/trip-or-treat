import styled from 'styled-components';
import { CommonContainer } from '../CommonContainer';

const ReviewListCategory = () => {
  return (
    <Container>
      <Index>번호</Index>
      <Review>리뷰</Review>
      {/* 이름 글자 수 공백 포함 11자로 제한 or 말줄임표 */}
      <Place>장소</Place>
      <Score>평점</Score>
      <CreatedDate>작성일</CreatedDate>
    </Container>
  );
};

export default ReviewListCategory;

const Container = styled.div`
  ${CommonContainer}

  margin-bottom: 7px;

  border-bottom: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const Index = styled.div`
  width: 35px;
  padding-right: 8px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const Review = styled.div`
  width: 260px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const Place = styled.div`
  width: 160px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const Score = styled.div`
  width: 115px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const CreatedDate = styled.div`
  width: 90px;
`;
