import styled from 'styled-components';

interface Props {
  id: number;
  src: string;
  name: string;
  onOpen: (ismodal: boolean) => void;
  setCurrentId: (id: number) => void;
}
const RegionItem = ({ id, src, name, onOpen, setCurrentId }: Props) => {
  const handleClickModal = () => {
    onOpen(true);
    setCurrentId(id);
  };

  return (
    <div>
      <Container onClick={handleClickModal}>
        <RegionImg src={src} />
        <RegionName>{name}</RegionName>
      </Container>
    </div>
  );
};

export default RegionItem;

const Container = styled.button`
  width: ${(props) => props.theme.size.regionItemSize};
  height: ${(props) => props.theme.size.regionItemSize};

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

  margin: 13px auto;

  text-align: center;

  font-family: 'Pretendard-Regular';
  font-size: 21px;
  color: ${(props) => props.theme.colors.blackFont};
`;
