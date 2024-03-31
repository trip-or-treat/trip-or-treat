import styled from 'styled-components';
import { useRecoilValue } from 'recoil';

import regionsAtom from 'src/atoms/regionsAtom';
import { SchedulesType } from 'src/@types/api/schedules';
import PlanTitle from './PlanTitle';

interface Props {
  code?: string;
  startDate: string;
  endDate: string;
  regions: number[];
  schedules: SchedulesType['schedules'];
}

const parseName = (name: string) => {
  return name.length > 5 ? name.slice(0, 2) : name;
};

const calculateExpense = (schedules: SchedulesType['schedules']) => {
  const dailyExpenses = schedules?.map(({ schedulePlaces }) =>
    schedulePlaces?.map((info) => info.expense ?? 0).reduce((a, c) => a + c, 0),
  );
  const totalExpense = dailyExpenses?.reduce((a, c) => a + c, 0);
  return totalExpense?.toLocaleString();
};

const PlanNavBar = ({ code, startDate, endDate, regions, schedules }: Props) => {
  const data = useRecoilValue(regionsAtom);
  const result = data.filter((item) => regions?.includes(item.id));

  return (
    <Wrapper>
      <section>
        <RegionListAndDateBox>
          <Ul>
            {result.map((item) => (
              <Li key={item.id}>{parseName(item.name)}</Li>
            ))}
          </Ul>
          <P> {`${startDate} ~ ${endDate}`}</P>
        </RegionListAndDateBox>
      </section>
      <TitleBox>
        <PlanTitle code={code} />
      </TitleBox>

      <TotalExpenseBox>{`총 경비 : ${calculateExpense(schedules)}원`}</TotalExpenseBox>
    </Wrapper>
  );
};

export default PlanNavBar;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  margin-bottom: 50px;

  & > section {
    display: flex;
    flex-direction: column;
    width: 33%;
  }
`;

const RegionListAndDateBox = styled.div`
  display: flex;
  align-items: flex-end;
`;

const Ul = styled.ul`
  display: flex;
`;

const Li = styled.li`
  margin-right: 20px;

  color: ${(props) => props.theme.colors.blackFont};
  font-size: 25px;
  font-family: 'Pretendard-Bold';
`;

const P = styled.p`
  color: ${(props) => props.theme.colors.darkGrey};
  font-size: 15px;
  font-family: 'Pretendard-Medium';
`;

const TitleBox = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 33%;
  padding: 10px;
`;
const TotalExpenseBox = styled.section`
  text-align: end;
`;
