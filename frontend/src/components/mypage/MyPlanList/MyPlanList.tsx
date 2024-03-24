import styled from 'styled-components';

import { CommonContainer } from '../../../styles/CommonContainer';
import { ReactComponent as DeleteIcon } from '../../../assets/svgs/delete.svg';

interface Props {
  idx: number;
  title: string;
  regions: string[];
  startDate: string;
  endDate: string;
  createdDate: string;
}

const MyPlanList = ({ idx, title, regions, startDate, endDate, createdDate }: Props) => {
  return (
    <PlanContainer>
      <Index>{idx}</Index>
      <PlanTitle>{title}</PlanTitle>
      <Region>{regions.join(' ')}</Region>
      <Period>
        {startDate} ~ {endDate}
      </Period>
      <CreatedAt>{createdDate}</CreatedAt>
      <DeleteIcon />
    </PlanContainer>
  );
};

export default MyPlanList;

const PlanContainer = styled.div`
  ${CommonContainer}

  border-bottom: solid 1px ${(props) => props.theme.colors.lightGrey};

  cursor: pointer;

  svg {
    width: 28px;

    &:hover {
      fill: ${(props) => props.theme.colors.mainColor};
    }
  }
`;

const Index = styled.div`
  width: 40px;
  text-align: center;
`;

const PlanTitle = styled.div`
  width: 215px;
  padding-left: 20px;
`;

const Region = styled.div`
  width: 130px;
  padding-left: 20px;
`;

const Period = styled.div`
  width: 198px;
  font-size: 12px;
  text-align: center;
`;

const CreatedAt = styled.div`
  width: 115px;
  font-size: 12px;
  text-align: center;
`;
