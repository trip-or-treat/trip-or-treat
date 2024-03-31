import React from 'react';
import styled from 'styled-components';
import { useRecoilValue } from 'recoil';
import { DragDropContext, Draggable, DropResult, Droppable } from 'react-beautiful-dnd';

import curDayAtom from 'src/atoms/curDayAtom';
import usePageState from 'src/hooks/usePageState';

import PlaceCard from '../PlaceList/PlaceCard';
import EmptyBox from '../common/EmptyBox';

const SelectedPlaceCardList = () => {
  const curDay = useRecoilValue(curDayAtom);
  const { totalPlan, setTotalPlan } = usePageState();

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
      {filteredCurDay[0]?.items.length === 0 && (
        <EmptyBox>날짜별로 장소를 1개 이상 선택해주세요!</EmptyBox>
      )}
      <DragDropContext onDragEnd={onDragEnd}>
        <Droppable droppableId="dragList">
          {(provided) => (
            <ul ref={provided.innerRef} {...provided.droppableProps}>
              {filteredCurDay.map((itemArr) => (
                <React.Fragment key={itemArr.date}>
                  {itemArr?.items?.map((item, index) => (
                    <Draggable key={item.placeId} draggableId={String(item.placeId)} index={index}>
                      {(magic) => (
                        <li ref={magic.innerRef} {...magic.draggableProps}>
                          <PlaceCard placeCardItem={item} type="DRAG_AND_DROP" magic={magic} />
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
