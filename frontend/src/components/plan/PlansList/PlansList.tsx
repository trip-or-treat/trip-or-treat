import styled from 'styled-components';

import { useCarousel } from 'src/hooks/useCarousel';

import { ReactComponent as ChevronLeft } from '../../../assets/svgs/chevronLeft.svg';
import { ReactComponent as ChevronRight } from '../../../assets/svgs/chevronRight.svg';
import PlansListCard from './PlansListCard';

interface Props {
  schedules: {
    date: string;
    schedulePlaces: {
      name: string;
      imageThumbnail: string;
      subCategoryName: string;
      placeId: number;
      visitOrder: number;
      memo: string;
      expense: number;
    }[];
  }[];
}

export interface SchedulePlacesType {
  name: string;
  imageThumbnail: string;
  subCategoryName: string;
  placeId: number;
  visitOrder: number;
  memo: string;
  expense: number;
}

const calculateDailyExpense = (schedulePlaces: SchedulePlacesType[]) => {
  const totalExpense = schedulePlaces.reduce((total, item) => total + (item.expense ?? 0), 0);
  return totalExpense.toLocaleString();
};

const PlansList = ({ schedules }: Props) => {
  const { handlePrev, handleNext, slideRef, currentSlide } = useCarousel(schedules.length % 2, 150);

  return (
    <>
      <Wrapper ref={slideRef} $length={schedules.length}>
        {schedules.map(({ date, schedulePlaces }, listIdx) => (
          <PlanListBox key={date}>
            <DayTitle>{`${listIdx + 1}일차 경비 : ${calculateDailyExpense(schedulePlaces)}원`}</DayTitle>
            {schedulePlaces.map((data, idx) => (
              <PlansListCard key={data.placeId} listIdx={listIdx} idx={idx} data={data} />
            ))}
          </PlanListBox>
        ))}
      </Wrapper>

      {currentSlide === 1 && (
        <LeftButton onClick={handlePrev} $length={schedules.length}>
          <ChevronLeft />
        </LeftButton>
      )}
      {currentSlide === 0 && (
        <RightButton onClick={handleNext} $length={schedules.length}>
          <ChevronRight />
        </RightButton>
      )}
    </>
  );
};

export default PlansList;

const Wrapper = styled.div<{ $length: number }>`
  display: flex;
  justify-content: ${(props) => (props.$length <= 4 ? 'center' : 'none')};
  width: ${(props) => (props.$length <= 4 ? '90vw' : '100vw')};
`;

const PlanListBox = styled.div`
  overflow: auto;
  flex: 0 0 calc(25% - 30px);
  height: 65vh;
`;

const DayTitle = styled.p`
  position: fixed;

  width: 100%;
  padding: 10px;

  background-color: white;

  color: ${(props) => props.theme.colors.blackFont};
  font-size: 16px;
  font-family: 'Pretendard-SemiBold';
`;

const MoveButtonBox = styled.button<{ $length: number }>`
  position: absolute;
  top: 50%;

  display: ${(props) => (props.$length <= 4 ? 'none' : 'flex')};
  justify-content: center;
  align-items: center;

  border: none;

  background-color: inherit;
  cursor: pointer;

  svg {
    width: 15px;
    fill: ${(props) => props.theme.colors.blackFont};
  }
`;

const LeftButton = styled(MoveButtonBox)`
  left: 25px;
`;

const RightButton = styled(MoveButtonBox)`
  right: 25px;
`;
