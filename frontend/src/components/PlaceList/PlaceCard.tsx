import styled from 'styled-components';
import { useRecoilState, useRecoilValue } from 'recoil';
import { useParams } from 'react-router-dom';
import { DraggableProvided } from 'react-beautiful-dnd';

import { PlaceListTypes } from 'src/@types/api/placeList';
import totalPlanAtom from 'src/atoms/totalPlanAtom';
import placeClickedIdListAtom from 'src/atoms/placeClickedIdListAtom';
import curDayAtom from 'src/atoms/curDayAtom';

import { ReactComponent as Plus } from '../../assets/svgs/plus.svg';

import PlaceCardDetailBtn from './PlaceCardDetailBtn';
import DragPlaceCard from './DragPlaceCard';

interface Props {
  placeCardItem: PlaceListTypes;
  type: 'ADD_BUTTON' | 'DELETE_BUTTON' | 'DRAG_AND_DROP';
  magic?: DraggableProvided | undefined;
}

const PlaceCard = ({ placeCardItem, type, magic }: Props) => {
  const curDay = useRecoilValue(curDayAtom);

  const [clickedIdList, setClickedIdList] = useRecoilState(placeClickedIdListAtom);
  const [totalPlan, setTotalPlan] = useRecoilState(totalPlanAtom);

  const { regionId } = useParams();

  const isClicked =
    clickedIdList &&
    clickedIdList[curDay - 1]?.map((data) => data.placeId).includes(placeCardItem?.placeId);

  const handleAddClick = (newItem: PlaceListTypes) => {
    if (totalPlan[curDay - 1].items.length === 7) return;

    const copyData = [...totalPlan];
    const updatedItem = [...totalPlan[curDay - 1].items, { ...newItem, regionId }];
    copyData[curDay - 1] = { ...copyData[curDay - 1], items: updatedItem };

    setTotalPlan(copyData);
    setClickedIdList(copyData?.map((data) => data.items));
  };

  if (type === 'DRAG_AND_DROP') {
    return <DragPlaceCard placeCardItem={placeCardItem} magic={magic} />;
  }

  return (
    <Wrapper data-id={placeCardItem.placeId}>
      <PlaceCardDetailBtn placeCardItem={placeCardItem} />

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
