import React from 'react';
import { Link, useParams } from 'react-router-dom';
import { Regions } from 'src/@types/api/regions';
import styled from 'styled-components';

interface Props {
  type: 'LINK' | 'BUTTON';
  item:
    | Regions
    | {
        id: number;
        name: string;
      };
  curRegionId?: number;
  setCurRegionId?: React.Dispatch<React.SetStateAction<number>>;
}

const parseName = (name: string) => {
  return name.length > 5 ? name.slice(0, 2) : name;
};

const RegionCategoryCard = ({ type = 'LINK', item, curRegionId, setCurRegionId }: Props) => {
  const { regionId } = useParams();

  return (
    <>
      {type === 'LINK' && (
        <LinkBox key={item.id} to={`/place/${item.id}`} $isClicked={item.id === Number(regionId)}>
          <TitleBox $isClicked={item.id === Number(regionId)}>{parseName(item.name)}</TitleBox>
        </LinkBox>
      )}

      {type === 'BUTTON' && (
        <TitleBox
          data-id={item.id}
          onClick={(e) => setCurRegionId && setCurRegionId(Number(e.currentTarget.dataset.id))}
          $isClicked={item.id === curRegionId}
        >
          {parseName(item.name)}
        </TitleBox>
      )}
    </>
  );
};

export default RegionCategoryCard;

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

  color: ${(props) =>
    props.$isClicked ? props.theme.colors.mainColor : props.theme.colors.darkGrey};
  font-family: ${(props) => (props.$isClicked ? 'Pretendard-SemiBold' : 'Pretendard-Light')};
  font-size: 21px;
  cursor: pointer;
`;
