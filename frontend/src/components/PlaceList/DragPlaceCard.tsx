import React, { ChangeEvent, useEffect, useState } from 'react';
import styled from 'styled-components';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { DraggableProvided } from 'react-beautiful-dnd';

import { PlaceListTypes } from 'src/@types/api/placeList';
import totalPlanAtom from 'src/atoms/totalPlanAtom';
import placeClickedIdListAtom from 'src/atoms/placeClickedIdListAtom';
import curDayAtom from 'src/atoms/curDayAtom';
import isOpeningMemoAtom from 'src/atoms/isOpeningMemoAtom';

import { ReactComponent as Minus } from '../../assets/svgs/minus.svg';
import { ReactComponent as Bars } from '../../assets/svgs/bars.svg';
import { ReactComponent as Pen } from '../../assets/svgs/pen.svg';

import PlaceCardDetailBtn from './PlaceCardDetailBtn';
import Accordion from '../Accordion';

interface Props {
  placeCardItem: PlaceListTypes;
  magic: DraggableProvided | undefined;
}

const DragPlaceCard = ({ placeCardItem, magic }: Props) => {
  const [isOpenMemo, setIsOpenMemo] = useState(false);
  const [expense, setExpense] = useState(0);
  const [memo, setMemo] = useState('');
  const [curScreenY, setCurScreenY] = useState(0);

  const [totalPlan, setTotalPlan] = useRecoilState(totalPlanAtom);
  const [isOpeningMemo, setIsOpeningMemo] = useRecoilState(isOpeningMemoAtom);
  const setClickedIdList = useSetRecoilState(placeClickedIdListAtom);
  const curDay = useRecoilValue(curDayAtom);

  const handleDeleteClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    const targetId = Number((e.currentTarget.parentNode as HTMLElement)?.dataset.id);
    if (!targetId) return;
    const copyData = [...totalPlan];
    const updatedItem = totalPlan[curDay - 1].items.filter((item) => item.id !== targetId);
    copyData[curDay - 1] = { ...copyData[curDay - 1], items: updatedItem };

    setTotalPlan(copyData);
    setClickedIdList(copyData?.map((data) => data.items));
  };

  const expenseChange = (e: ChangeEvent<HTMLInputElement>) => {
    const curValue = Number(e.target.value.replaceAll(',', ''));
    if (Number.isNaN(curValue)) {
      setExpense(0);
    } else {
      setExpense(curValue);
    }
  };

  const handleSave = () => {
    const copyData = [...totalPlan];
    const targetIndex = curDay - 1;
    const targetItemIdx = copyData[targetIndex].items.findIndex(
      (data) => data.id === placeCardItem.id,
    );

    if (targetItemIdx !== -1) {
      const targetItem = copyData[targetIndex].items[targetItemIdx];
      const updatedItem = { ...targetItem, memo, expense };
      const updatedItems = [...copyData[targetIndex].items];

      updatedItems[targetItemIdx] = updatedItem;

      copyData[targetIndex] = {
        ...copyData[targetIndex],
        items: updatedItems,
      };
    }

    setIsOpenMemo(false);
    setIsOpeningMemo(false);
    setTotalPlan(copyData);
  };

  const handleClickMemo = (e: React.MouseEvent<HTMLButtonElement>) => {
    setCurScreenY(e.nativeEvent.screenY);
    if (!isOpeningMemo) {
      setIsOpenMemo(true);
      setIsOpeningMemo(true);
    }
  };

  useEffect(() => {
    setIsOpeningMemo(false);
  }, [curDay, totalPlan]);

  return (
    <Accordion isOpenMemo={isOpenMemo} curScreenY={curScreenY}>
      <Wrapper data-id={placeCardItem.id}>
        <PlaceCardDetailBtn placeCardItem={placeCardItem} />

        <IconButton onClick={handleClickMemo}>
          <Pen />
        </IconButton>
        <IconButton onClick={handleDeleteClick}>
          <Minus />
        </IconButton>
        <IconButton {...magic?.dragHandleProps}>
          <Bars />
        </IconButton>
      </Wrapper>

      <ContentBox>
        <Form>
          <Label htmlFor="expense">
            <p>예상경비</p>
            <input
              id="expense"
              maxLength={11}
              placeholder="ex) 3,000"
              onChange={expenseChange}
              value={expense.toLocaleString()}
            />
            <p>원</p>
          </Label>

          <Label htmlFor="memo">
            <p>메모</p>
            <textarea
              id="memo"
              placeholder="(최대 20자)"
              maxLength={20}
              onChange={(e) => setMemo(e.target.value)}
              value={memo}
            />
          </Label>
          <CloseBtn type="button" onClick={handleSave}>
            저장
          </CloseBtn>
        </Form>
      </ContentBox>
    </Accordion>
  );
};

export default DragPlaceCard;

const Wrapper = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
  height: 60px;

  border: none;
  background-color: white;
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

const ContentBox = styled.div`
  position: relative;

  width: 100%;
  height: 200px;

  padding: 25px 20px;

  box-sizing: border-box;
`;

const CloseBtn = styled.button`
  position: absolute;
  bottom: 15px;
  left: 45%;

  padding: 5px 15px;

  border-radius: 5px;
  border: none;
  background-color: ${(props) => props.theme.colors.mainColor};

  color: white;
  cursor: pointer;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  display: flex;
  align-items: center;

  margin: 10px 0px;

  font-size: 13px;

  p {
    width: 50px;
  }

  input,
  textarea {
    margin: 0px 5px 0px 15px;
    padding: 3px;

    border: 1px solid rgba(0, 0, 0, 0.3);

    outline: none;

    &::placeholder {
      display: flex;
      font-size: 11px;
    }
  }

  textarea {
    width: 80%;
    height: 60px;
    resize: none;
  }
`;
