import styled from 'styled-components';
import { useState } from 'react';
import { Link } from 'react-router-dom';

import AlertModal from 'src/components/AlertModal';
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
  const [open, setOpen] = useState(false);

  const onClose = () => {
    setOpen(false);
  };

  return (
    <PlanContainer>
      <Index>{idx}</Index>
      {/* 제목 클릭하면 mypage/plans/:planId 페이지로 보내주기 */}
      <PlanTitle to="/mypage/plans/:planId">{title}</PlanTitle>
      <Region>{regions.join(' ')}</Region>
      <Period>
        {startDate} ~ {endDate}
      </Period>
      <CreatedAt>{createdDate}</CreatedAt>
      <DeleteIcon onClick={() => setOpen(true)} />
      {open && (
        <AlertModal
          onButtonText="삭제"
          offButtonText="취소"
          path="/mypage/myPlan"
          onClose={onClose}
          // onClick={삭제 api}
        >
          정말 삭제하시겠어요?
        </AlertModal>
      )}
    </PlanContainer>
  );
};

export default MyPlanList;

const PlanContainer = styled.div`
  ${CommonContainer}

  border-bottom: solid 1px ${(props) => props.theme.colors.lightGrey};

  svg {
    width: 28px;

    cursor: pointer;

    &:hover {
      fill: ${(props) => props.theme.colors.mainColor};
    }
  }
`;

const Index = styled.div`
  width: 40px;

  text-align: center;

  cursor: default;
`;

const PlanTitle = styled(Link)`
  width: 215px;
  padding-left: 20px;

  color: ${(props) => props.theme.colors.blackFont};
  text-decoration: none;

  cursor: pointer;

  &:hover {
    text-decoration: solid 1px ${(props) => props.theme.colors.blackFont};
  }
`;

const Region = styled.div`
  width: 130px;
  padding-left: 20px;

  cursor: default;
`;

const Period = styled.div`
  width: 198px;

  font-size: 12px;
  text-align: center;

  cursor: default;
`;

const CreatedAt = styled.div`
  width: 115px;

  font-size: 12px;
  text-align: center;

  cursor: default;
`;
