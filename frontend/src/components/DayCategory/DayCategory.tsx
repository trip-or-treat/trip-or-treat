import { useRecoilState, useRecoilValue } from 'recoil';
import styled from 'styled-components';

import curDayAtom from 'src/atoms/curDayAtom';
import totalPlanAtom from 'src/atoms/totalPlanAtom';

const getDate = (date: string) => {
  return date.split(' ').join('.').slice(5);
};

const DayCategory = () => {
  const totalPlan = useRecoilValue(totalPlanAtom);
  const [curDay, setCurDay] = useRecoilState(curDayAtom);

  return (
    <>
      <SelectedDayTitle>
        <p>{`DAY ${curDay}`}</p>
        <p>{getDate(totalPlan[curDay - 1].date)}</p>
      </SelectedDayTitle>

      <DayButtonBox>
        {totalPlan.map((item) => (
          <DayButton
            key={item.day}
            onClick={() => setCurDay(item.day)}
            $isClicked={curDay === item.day}
          >
            <Content>{`Day - ${item.day}`}</Content>
            <Content>{getDate(item.date)}</Content>
          </DayButton>
        ))}
      </DayButtonBox>
    </>
  );
};

export default DayCategory;

const SelectedDayTitle = styled.div`
  display: flex;
  align-items: flex-end;
  padding-bottom: 20px;

  p {
    margin: 0px 5px;

    &:first-child {
      font-size: 25px;
      color: ${(props) => `${props.theme.colors.blackFont}`};
      font-family: 'Pretendard-Bold';
    }

    &:last-child {
      font-size: 15px;

      color: ${(props) => `${props.theme.colors.darkGrey}`};
      font-family: 'Pretendard-Medium';
    }
  }
`;

const DayButtonBox = styled.ul`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;

  width: 100%;
`;

const DayButton = styled.button<{ $isClicked: boolean }>`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  padding: 10px;

  border-radius: 25px;
  border: none;

  background-color: ${(props) =>
    props.$isClicked ? `${props.theme.colors.mainColor}` : 'rgba(141, 132, 132, 0.5)'};
  color: white;

  font-size: 12px;
  cursor: pointer;
`;

const Content = styled.p`
  &:first-child {
    font-size: 12px;
    font-family: 'Pretendard-Medium';
    margin-bottom: 2px;
  }

  &:last-child {
    font-size: 9px;
  }
`;
