import styled from 'styled-components';
import React from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import { PlaceListTypes } from 'src/@types/api/placeList';
import totalPlanAtom from 'src/atoms/totalPlanAtom';
import placeClickedIdListAtom from 'src/atoms/placeClickedIdListAtom';

import curDayAtom from 'src/atoms/curDayAtom';
import { ReactComponent as Plus } from '../../assets/svgs/plus.svg';
import { ReactComponent as Minus } from '../../assets/svgs/minus.svg';
import defaultimg from '../../assets/images/defaultImg.png';

interface Props {
  placeCardItem: PlaceListTypes;
  type: 'ADD_BUTTON' | 'DELETE_BUTTON' | 'DRAG_AND_DROP';
}

const PlaceCard = ({ placeCardItem, type }: Props) => {
  const [clickedIdList, setClickedIdList] = useRecoilState(placeClickedIdListAtom);
  const [totalPlan, setTotalPlan] = useRecoilState(totalPlanAtom);
  const curDay = useRecoilValue(curDayAtom);

  const isClicked =
    clickedIdList && clickedIdList[curDay - 1]?.map((data) => data.id).includes(placeCardItem.id);

  const handleDeleteClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    const targetId = Number((e.currentTarget.parentNode?.parentNode as HTMLElement)?.dataset.id);
    if (!targetId) return;

    const copyData = [...totalPlan];
    const updatedItem = totalPlan[curDay - 1].items.filter((item) => item.id !== targetId);
    copyData[curDay - 1] = { ...copyData[curDay - 1], items: updatedItem };

    setTotalPlan(copyData);
    setClickedIdList(copyData.map((data) => data.items));
  };

  const handleAddClick = (newItem: PlaceListTypes) => {
    const copyData = [...totalPlan];
    const updatedItem = [...totalPlan[curDay - 1].items, newItem];
    copyData[curDay - 1] = { ...copyData[curDay - 1], items: updatedItem };

    setTotalPlan(copyData);
    setClickedIdList(copyData?.map((data) => data.items));
  };

  const handleClickModal = () => {
    // TODO : 지역 상세 Modal open 되도록 처리
  };

  return (
    <Wrapper data-id={placeCardItem.id}>
      <DetailButton onClick={handleClickModal} disabled={type === 'DRAG_AND_DROP'}>
        <ThumbnailImg src={placeCardItem.imageThumbnail || defaultimg} />
        <RegionNameBox>
          <p>{placeCardItem.name}</p>
          <p>{placeCardItem.subCategoryName}</p>
        </RegionNameBox>
      </DetailButton>

      <IconButtonBox>
        {type === 'ADD_BUTTON' && (
          <IconButton
            onClick={() => handleAddClick(placeCardItem)}
            disabled={isClicked}
            $isClicked={isClicked}
          >
            <Plus />
          </IconButton>
        )}
        {type === 'DELETE_BUTTON' && (
          <IconButton onClick={handleDeleteClick}>
            <Minus />
          </IconButton>
        )}

        {type === 'DRAG_AND_DROP' && (
          <>
            <IconButton onClick={handleDeleteClick}>
              <Minus />
            </IconButton>
            <IconButton>=</IconButton>
          </>
        )}
      </IconButtonBox>
    </Wrapper>
  );
};

export default PlaceCard;

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

const RegionNameBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  color: ${(props) => props.theme.colors.blackFont};

  p {
    font-family: 'Pretendard-Regular';
    font-size: 17px;

    &:last-child {
      font-size: 10px;
      color: grey;
    }
  }
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
