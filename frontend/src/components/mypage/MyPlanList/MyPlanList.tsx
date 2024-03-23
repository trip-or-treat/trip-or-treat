import styled from 'styled-components';

import { CommonContainer } from '../CommonContainer';
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

  border-radius: 35px;
  box-shadow: 0px 2px 4px 0px ${(props) => props.theme.colors.lightGrey};

  cursor: pointer;

  svg {
    width: 28px;

    &:hover {
      filter: invert(16%) sepia(89%) saturate(6051%) hue-rotate(358deg) brightness(85%)
        contrast(113%);
    }
  }
`;

const Index = styled.div`
  width: 30px;
`;

const PlanTitle = styled.div`
  width: 235px;
`;

const Region = styled.div`
  width: 130px;
`;

const Period = styled.div`
  width: 200px;
  font-size: 12px;
`;

const CreatedAt = styled.div`
  width: 90px;
  font-size: 12px;
`;
