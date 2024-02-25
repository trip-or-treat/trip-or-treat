import styled from 'styled-components';

// TODO
// 데이터 받아와서 지역명 띄워주기

const RegionName = () => {
  return <Name>서울</Name>;
};

export default RegionName;

const Name = styled.div`
  display: block;

  margin: 20px auto;

  text-align: center;

  font-family: 'Pretendard-Regular';
  font-size: 30px;
  color: ${(props) => props.theme.colors.blackFont};
`;
