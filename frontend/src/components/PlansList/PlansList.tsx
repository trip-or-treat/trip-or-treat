import styled from 'styled-components';
import { useRecoilValue } from 'recoil';

import totalPlanAtom from 'src/atoms/totalPlanAtom';
import { useCarousel } from 'src/hooks/useCarousel';
import PlansListCard from './PlansListCard';

import { ReactComponent as ChevronLeft } from '../../assets/svgs/chevronLeft.svg';
import { ReactComponent as ChevronRight } from '../../assets/svgs/chevronRight.svg';

const PlansList = () => {
  const totalPlan = useRecoilValue(totalPlanAtom);
  const { handlePrev, handleNext, slideRef, currentSlide } = useCarousel(totalPlan.length % 2, 323);

  return (
    <>
      <PlansListWrapper ref={slideRef} $length={totalPlan.length}>
        {totalPlan.map((item, listIdx) => (
          <Wrapper key={item.day}>
            <DayTitle>{`${item.day}일차`}</DayTitle>

            {item.items.map((data, idx) => (
              <PlansListCard key={data.id} listIdx={listIdx} idx={idx} data={data} />
            ))}
          </Wrapper>
        ))}
      </PlansListWrapper>

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

const Wrapper = styled.div`
  overflow: auto;

  width: 323px;
  height: 58vh;
`;

const DayTitle = styled.p`
  font-size: 16px;
  font-family: 'Pretendard-SemiBold';
  color: ${(props) => props.theme.colors.blackFont};
`;

const MoveButtonBox = styled.button<{ $length: number }>`
  display: ${(props) => (props.$length <= 4 ? 'none' : 'flex')};
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 50%;

  border: none;

  background-color: inherit;

  cursor: pointer;

  svg {
    width: 15px;
    fill: ${(props) => props.theme.colors.blackFont};
  }
`;

const PlansListWrapper = styled.div<{ $length: number }>`
  display: grid;
  grid-auto-flow: column;
  justify-content: ${(props) => (props.$length <= 4 ? 'center' : 'none')};

  margin-top: 25px;
`;

const LeftButton = styled(MoveButtonBox)`
  left: 25px;
`;

const RightButton = styled(MoveButtonBox)`
  right: 25px;
`;
