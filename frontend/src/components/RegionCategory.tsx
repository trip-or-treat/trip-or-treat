import styled from 'styled-components';
import { Link, useParams } from 'react-router-dom';
import { Regions } from 'src/@types/api/regions';

interface Props {
  data: Regions[];
}

const parseName = (name: string) => {
  return name.length > 5 ? name.slice(0, 2) : name;
};

const RegionCategory = ({ data }: Props) => {
  const { regionId } = useParams();

  return (
    <Wrapper>
      {data.map((item) => (
        <LinkBox key={item.id} to={`/place/${item.id}`} $isClicked={item.id === Number(regionId)}>
          <TitleBox $isClicked={item.id === Number(regionId)}>{parseName(item.name)}</TitleBox>
        </LinkBox>
      ))}
    </Wrapper>
  );
};

export default RegionCategory;

const Wrapper = styled.ul`
  display: flex;
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
  margin: 0px 10px;
  border-bottom: ${(props) =>
    props.$isClicked ? `1px solid ${props.theme.colors.mainColor}` : 'none'};

  font-family: ${(props) => (props.$isClicked ? 'Pretendard-SemiBold' : 'Pretendard-Light')};
  font-size: 21px;
`;
