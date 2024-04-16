import styled from 'styled-components';

import media from 'src/styles/media';
import { CommonContainer } from '../../../styles/CommonContainer';

const PlanListCategory = () => {
  return (
    <Container>
      <Index>번호</Index>
      <Title>계획명</Title>
      <Region>여행 지역</Region>
      <Period>여행 기간</Period>
      <CreatedDate>작성일</CreatedDate>
    </Container>
  );
};

export default PlanListCategory;

const Container = styled.div`
  ${CommonContainer}

  border-bottom: solid 1px ${(props) => props.theme.colors.lightGrey};
  text-align: center;

  ${media.mobile`
    padding: 5px 0;
    font-size: 18px;
  `}
`;

const Index = styled.div`
  width: 40px;
  padding-right: 8px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};

  ${media.mobile`
    width: 70px;
  `}
`;

const Title = styled.div`
  width: 227px;
  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};

  ${media.mobile`
    width: 267px;
  `}
`;

const Region = styled.div`
  width: 149px;
  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};

  ${media.mobile`
    width: 179px;
  `}
`;

const Period = styled.div`
  width: 197px;
  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};

  ${media.mobile`
    width: 237px;
  `}
`;

const CreatedDate = styled.div`
  width: 110px;

  ${media.mobile`
    width: 140px;
  `}
`;
