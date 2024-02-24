import styled from 'styled-components';
import { Link, useParams } from 'react-router-dom';

interface Props {
  data: {
    id: number;
    name: string;
    imageOrigin: string;
    imageThumbnail: string;
    latitude: number;
    longitude: number;
  }[];
}

const RegionCategory = ({ data }: Props) => {
  const { regionId } = useParams();

  return (
    <Wrapper>
      {data.map((item) => (
        <LinkBox key={item.id} to={`/place/${item.id}`} $isClicked={item.id === Number(regionId)}>
          <TitleBox $isClicked={item.id === Number(regionId)}>{item.name}</TitleBox>
        </LinkBox>
      ))}
    </Wrapper>
  );
};

export default RegionCategory;

const Wrapper = styled.ul`
  display: flex;

  padding: 10px 0px;
`;

const LinkBox = styled(Link)<{ $isClicked: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  margin: 0px 5px;

  color: ${(props) =>
    props.$isClicked ? props.theme.colors.mainColor : props.theme.colors.darkGrey};

  text-decoration: none;
`;

const TitleBox = styled.p<{ $isClicked: boolean }>`
  padding: 5px 2px;
  margin: 0px 5px;
  border-bottom: ${(props) =>
    props.$isClicked ? `1px solid ${props.theme.colors.mainColor}` : 'none'};

  font-weight: 600;
  font-size: 27px;
`;
