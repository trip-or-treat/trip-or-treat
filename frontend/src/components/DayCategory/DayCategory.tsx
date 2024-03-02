import React from 'react';
import styled from 'styled-components';

interface Props {
  curDay: number;
  setCurDay: React.Dispatch<React.SetStateAction<number>>;
}

const DAY_DATA = [
  { day: 1, date: '02 21', items: [] },
  { day: 2, date: '02 22', items: [] },
  { day: 3, date: '02 23', items: [] },
  { day: 4, date: '02 24', items: [] },
  { day: 5, date: '02 25', items: [] },
  { day: 6, date: '02 26', items: [] },
  { day: 7, date: '02 27', items: [] },
];

const getDate = (date: string) => {
  return date.split(' ').join('.');
};

const DayCategory = ({ curDay, setCurDay }: Props) => {
  return (
    <>
      <SelectedDayTitle>
        <p>{`DAY ${curDay}`}</p>
        <p>{getDate(DAY_DATA[curDay - 1].date)}</p>
      </SelectedDayTitle>

      <DayButtonBox>
        {DAY_DATA.map((item) => (
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
    margin-bottom: 2px;
  }

  &:last-child {
    font-size: 9px;
  }
`;
