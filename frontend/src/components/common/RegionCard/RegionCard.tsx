import React from 'react';
import styled from 'styled-components';
import { useRecoilState } from 'recoil';

import { Regions } from 'src/@types/api/regions';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import regionClickedIdListAtom from 'src/atoms/regionClickedIdListAtom';

import { ReactComponent as Plus } from '../../../assets/svgs/plus.svg';
import { ReactComponent as Minus } from '../../../assets/svgs/minus.svg';
import defaultimg from '../../../assets/images/defaultImg.png';

interface Props {
  item: Regions;
  type: 'DEFAULT' | 'ADD_BUTTON' | 'DELETE_BUTTON';
}

const RegionCard = ({ item, type }: Props) => {
  const [clickedIdList, setClickedIdList] = useRecoilState(regionClickedIdListAtom);
  const [myRegionList, setMyRegionList] = useRecoilState(myRegionListAtom);

  const handleDeleteClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    const targetButton = e.currentTarget.parentNode?.parentNode as HTMLButtonElement;
    const targetCardId = Number(targetButton.dataset.id);
    const filterItemData = myRegionList.filter((data) => data.id !== targetCardId);

    setClickedIdList(filterItemData.map((data) => data.id));
    setMyRegionList(filterItemData);
  };

  const handleAddClick = (newItem: Regions) => {
    if (myRegionList.length >= 3) {
      alert('최대 2개만 선택 가능합니다');
      return;
    }

    const newRegionList = [...myRegionList, newItem];

    setClickedIdList(newRegionList.map((data) => data.id));
    setMyRegionList((prev) => [...prev, newItem]);
  };

  const handleClickModal = () => {
    // TODO : 지역 상세 Modal open 되도록 처리
  };

  return (
    <Wrapper data-id={item.id}>
      <DetailButton onClick={handleClickModal} disabled={type === 'DEFAULT'}>
        <ThumbnailImg src={item.imageThumbnail || defaultimg} />
        <RegionName>{item.name}</RegionName>
      </DetailButton>

      <IconButtonBox>
        {type === 'DEFAULT' && <DefaultMarker>메인 여행지</DefaultMarker>}

        {type === 'DELETE_BUTTON' && (
          <IconButton onClick={handleDeleteClick}>
            <Minus />
          </IconButton>
        )}

        {type === 'ADD_BUTTON' && (
          <IconButton
            onClick={() => handleAddClick(item)}
            disabled={clickedIdList.includes(item.id)}
            $isClicked={clickedIdList.includes(item.id)}
          >
            <Plus />
          </IconButton>
        )}
      </IconButtonBox>
    </Wrapper>
  );
};

export default RegionCard;

const Wrapper = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
  height: 60px;

  border: none;
  background-color: white;
`;

const DetailButton = styled.button`
  display: flex;
  align-items: center;

  width: 70%;

  border: none;
  background-color: inherit;

  cursor: pointer;
`;

const ThumbnailImg = styled.img`
  width: 35px;
  height: 35px;
  margin-right: 15px;

  border-radius: 50%;
`;

const RegionName = styled.p`
  font-size: 18px;
  color: ${(props) => props.theme.colors.blackFont};
`;

const DefaultMarker = styled.div`
  padding: 5px 10px;

  border-radius: 10px;
  background-color: ${(props) => props.theme.colors.lightGrey};

  color: ${(props) => props.theme.colors.blackFont};
  font-size: 9px;
  font-family: 'Pretendard-Medium';
`;

const IconButtonBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;

  width: 30%;
`;

const IconButton = styled.button<{ $isClicked?: boolean }>`
  position: relative;

  width: 20px;
  height: 20px;
  margin-left: 10px;

  border: none;
  background-color: inherit;

  cursor: pointer;

  svg {
    width: 20px;
    height: 20px;

    fill: ${(props) =>
      props.$isClicked ? props.theme.colors.darkGrey : props.theme.colors.blackFont};
  }
`;
