import styled from 'styled-components';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { END_POINTS } from 'src/constants/api';
import { loginApi } from 'src/api';

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
  planId: number;
}

const MyPlanList = ({ idx, title, regions, startDate, endDate, createdDate, planId }: Props) => {
  const [modalOpen, setModalOpen] = useState(false);

  const onModalClose = () => {
    setModalOpen(false);
  };

  const parseName = (name: string) => {
    return name.length > 5 ? name.slice(0, 2) : name;
  };

  const regionsNotSplit = regions.join(' ');
  const regionsSplit: string[] = regionsNotSplit.split(' ');

  const queryClient = useQueryClient();
  const deletePlanMutation = useMutation({
    mutationFn: () => loginApi.delete(`${END_POINTS.PLANS}/${planId}`),
    onSuccess: () => {
      onModalClose();
      return queryClient.invalidateQueries({
        queryKey: ['myPlanList'],
      });
    },
    onError: () => alert('삭제를 실패했습니다.'),
  });

  const handleDelete = () => {
    deletePlanMutation.mutate();
  };

  return (
    <PlanContainer>
      <Index>{idx}</Index>
      <PlanTitle to={`/mypage/plans/${planId}`}>{title}</PlanTitle>
      <Region>
        {regionsSplit.map((item, index) => (
          <React.Fragment key={item}>
            <p>{parseName(item)}</p>
            {index < regionsSplit.length - 1 && <span>&nbsp;</span>}
          </React.Fragment>
        ))}
      </Region>
      <Period>
        {startDate} ~ {endDate}
      </Period>
      <CreatedAt>{createdDate}</CreatedAt>
      <DeleteIcon onClick={() => setModalOpen(true)} />
      {modalOpen && (
        <AlertModal
          onButtonText="삭제"
          offButtonText="취소"
          path="/mypage/myPlan"
          onClose={onModalClose}
          onClick={handleDelete}
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
  display: flex;

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
