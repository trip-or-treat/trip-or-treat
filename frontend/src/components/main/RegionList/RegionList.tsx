import styled from 'styled-components';
import RegionItem from './RegionItem';

// TODO
// 데이터 받아와서 지역리스트 띄워주기
// 마지막 줄 왼쪽 정렬되게 할 수 있나 알아보기

const RegionList = () => {
  return (
    <Container>
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
      <RegionItem />
    </Container>
  );
};

export default RegionList;

const Container = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 270px);
  column-gap: 70px;
  row-gap: 170px;

  justify-content: center;
`;
