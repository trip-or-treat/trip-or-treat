import styled from 'styled-components';

import { CommonContainer } from '../../../styles/CommonContainer';

const PlanListCategory = () => {
  return (
    <Container>
      <Index>번호</Index>
      <Title>계획명</Title>
      {/* 이름 글자 수 공백 포함 11자로 제한 or 말줄임표 */}
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
`;

const Index = styled.div`
  width: 40px;
  padding-right: 8px;

  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const Title = styled.div`
  width: 227px;
  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const Region = styled.div`
  width: 149px;
  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const Period = styled.div`
  width: 197px;
  border-right: solid 1px ${(props) => props.theme.colors.lightGrey};
`;

const CreatedDate = styled.div`
  width: 110px;
  /* margin-left: -30px; */
`;
