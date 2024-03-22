import styled from 'styled-components';

import { TotalPlan } from 'src/atoms/totalPlanAtom';

import { Regions } from 'src/@types/api/regions';
import PlanTitle from './PlanTitle';

interface Props {
  totalPlan: TotalPlan[];
  myRegionList: Regions[];
}

const parseName = (name: string) => {
  return name.length > 5 ? name.slice(0, 2) : name;
};

const calculateExpense = (totalPlan: TotalPlan[]) => {
  const dailyExpenses = totalPlan.map((data) =>
    data.items.map((info) => info.expense ?? 0).reduce((a, c) => a + c, 0),
  );
  const totalExpense = dailyExpenses.reduce((a, c) => a + c, 0);
  return totalExpense.toLocaleString();
};

const PlanNavBar = ({ totalPlan, myRegionList }: Props) => {
  return (
    <Wrapper>
      <section>
        <RegionListAndDateBox>
          <Ul>{myRegionList?.map((item) => <Li key={item.id}>{parseName(item.name)}</Li>)}</Ul>
          <P> {`${totalPlan[0]?.date} ~ ${totalPlan[totalPlan.length - 1]?.date}`}</P>
        </RegionListAndDateBox>
      </section>
      <PlanTitle />

      <TotalExpenseBox>{`총 경비 : ${calculateExpense(totalPlan)}원`}</TotalExpenseBox>
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

const TotalExpenseBox = styled.div`
  text-align: end;
`;
