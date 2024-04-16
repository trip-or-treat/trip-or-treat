import styled from 'styled-components';

import media from 'src/styles/media';
import { CommonContainer } from '../../../styles/CommonContainer';

const ReviewListCategory = () => {
  return (
    <Container>
      <Index>번호</Index>
      <Review>리뷰</Review>
      <Place>장소</Place>
      <Score>평점</Score>
      <CreatedDate>작성일</CreatedDate>
    </Container>
  );
};

export default ReviewListCategory;

const Container = styled.div`
  ${CommonContainer}

  border-bottom: solid 1px ${(props) => props.theme.colors.lightGrey};
  text-align: center;

  ${media.mobile`
    font-size: 18px;
  `}
`;

const Index = styled.div`
  width: 35px;
  padding-right: 8px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};

  ${media.mobile`
    width: 55px;
  `}
`;

const Review = styled.div`
  width: 265px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};

  ${media.mobile`
    width: 290px;
  `}
`;

const Place = styled.div`
  width: 185px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};

  ${media.mobile`
    width: 215px;
  `}
`;

const Score = styled.div`
  width: 120px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};

  ${media.mobile`
    width: 150px;
  `}
`;

const CreatedDate = styled.div`
  width: 105px;

  ${media.mobile`
    width: 165px;
  `}
`;
