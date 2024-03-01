import styled from 'styled-components';

interface Props {
  src: string;
  name: string;
}
const RegionItem = ({ src, name }: Props) => {
  const handleClickModal = () => {
    // 클릭 시 지역 모달 열리게
  };

  return (
    <Container onClick={handleClickModal}>
      <RegionImg src={src} />
      <RegionName>{name}</RegionName>
    </Container>
  );
};

export default RegionItem;

const Container = styled.button`
  width: 270px;
  height: 270px;

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

const RegionName = styled.div`
  display: block;

  margin: 20px auto;

  text-align: center;

  font-family: 'Pretendard-Regular';
  font-size: 25px;
  color: ${(props) => props.theme.colors.blackFont};
`;
