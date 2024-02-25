import styled from 'styled-components';

import testImg from '../../../assets/images/testImg.png';
import RegionName from './RegionName';

// TODO
// 데이터 받아와서 이미지 띄워주기

const RegionItem = () => {
  return (
    <Container>
      <RegionImg src={testImg} />
      <RegionName />
    </Container>
  );
};

export default RegionItem;

const Container = styled.button`
  width: 300px;
  height: 300px;

  margin: 100px 30px;

  border: none;
  background-color: white;

  cursor: pointer;
`;

const RegionImg = styled.img<{ src: string }>`
  width: 100%;
  height: 100%;

  background-image: url(${(props) => props.src});
  border-radius: 30px;
  box-shadow: 0px 3px 5px 1px ${(props) => props.theme.colors.darkGrey};
`;
