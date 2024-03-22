import styled from 'styled-components';

import { TotalPlan } from 'src/atoms/totalPlanAtom';
import { useCarousel } from 'src/hooks/useCarousel';

import { PlaceListTypes } from 'src/@types/api/placeList';
import PlansListCard from './PlansListCard';

import { ReactComponent as ChevronLeft } from '../../../assets/svgs/chevronLeft.svg';
import { ReactComponent as ChevronRight } from '../../../assets/svgs/chevronRight.svg';

interface Props {
  totalPlan: TotalPlan[];
}

const calculateDailyExpense = (items: PlaceListTypes[]) => {
  const totalExpense = items.reduce((total, item) => total + (item.expense ?? 0), 0);
  return totalExpense.toLocaleString();
};

const PlansList = ({ totalPlan }: Props) => {
  const { handlePrev, handleNext, slideRef, currentSlide } = useCarousel(totalPlan.length % 2, 150);

  return (
    <>
      <Wrapper ref={slideRef} $length={totalPlan.length}>
        {totalPlan.map(({ day, items }, listIdx) => (
          <PlanListBox key={day}>
            <DayTitle>{`${day}일차 경비 : ${calculateDailyExpense(items)}원`}</DayTitle>
            {items.map((data, idx) => (
              <PlansListCard key={data.id} listIdx={listIdx} idx={idx} data={data} />
            ))}
          </PlanListBox>
        ))}
      </Wrapper>

      {currentSlide === 1 && (
        <LeftButton onClick={handlePrev} $length={totalPlan.length}>
          <ChevronLeft />
        </LeftButton>
      )}
      {currentSlide === 0 && (
        <RightButton onClick={handleNext} $length={totalPlan.length}>
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
