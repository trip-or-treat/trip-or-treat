import React from 'react';
import styled from 'styled-components';
import { useRecoilState, useRecoilValue } from 'recoil';
import { DragDropContext, Draggable, DropResult, Droppable } from 'react-beautiful-dnd';

import totalPlanAtom from 'src/atoms/totalPlanAtom';
import curDayAtom from 'src/atoms/curDayAtom';

import PlaceCard from '../PlaceList/PlaceCard';
import GhostImg from '../../assets/images/ghost.png';

const EmptyPlaceItem = () => {
  return (
    <EmptyPlaceItemWrapper>
      <p>날짜별로 장소를 1개 이상 선택해주세요!</p>
      <img src={GhostImg} alt="고스트 이미지" />
    </EmptyPlaceItemWrapper>
  );
};

const SelectedPlaceCardList = () => {
  const curDay = useRecoilValue(curDayAtom);

  const [totalPlan, setTotalPlan] = useRecoilState(totalPlanAtom);
  const filteredCurDay = totalPlan.filter((item) => item.day === curDay);

  const onDragEnd = ({ destination, source }: DropResult) => {
    const { items } = totalPlan[curDay - 1];
    const targetIdx = source.index;
    const destinationIdx = destination?.index ?? targetIdx;

    const copyData = [...items];
    const moveItem = items[targetIdx];

    copyData.splice(targetIdx, 1);
    copyData.splice(destinationIdx, 0, moveItem);

    setTotalPlan((prev) =>
      prev.map((plan, index) => (index === curDay - 1 ? { ...plan, items: copyData } : plan)),
    );
  };

  return (
    <Wrapper>
      {filteredCurDay[0].items.length === 0 && <EmptyPlaceItem />}
      <DragDropContext onDragEnd={onDragEnd}>
        <Droppable droppableId="dragList">
          {(provided) => (
            <ul ref={provided.innerRef} {...provided.droppableProps}>
              {filteredCurDay.map((itemArr) => (
                <React.Fragment key={itemArr.date}>
                  {itemArr.items.map((item, index) => (
                    <Draggable key={item.id} draggableId={String(item.id)} index={index}>
                      {(magic) => (
                        <li
                          ref={magic.innerRef}
                          {...magic.draggableProps}
                          {...magic.dragHandleProps}
                        >
                          <PlaceCard placeCardItem={item} type="DRAG_AND_DROP" />
                        </li>
                      )}
                    </Draggable>
                  ))}
                </React.Fragment>
              ))}
            </ul>
          )}
        </Droppable>
      </DragDropContext>
    </Wrapper>
  );
};

export default SelectedPlaceCardList;

const Wrapper = styled.div`
  margin-top: 20px;
`;

const EmptyPlaceItemWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 80px;
  font-size: 17px;
  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-Medium';

  img {
    margin-top: 20px;
    width: 80px;
    height: 80px;
  }
`;
