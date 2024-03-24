import { ReactNode } from 'react';
import styled from 'styled-components';
import { useMatch } from 'react-router-dom';

import { ReactComponent as MyInfoIcon } from '../../../assets/svgs/myInfo.svg';
import { ReactComponent as MyPlanIcon } from '../../../assets/svgs/myPlan.svg';
import { ReactComponent as MyReviewIcon } from '../../../assets/svgs/myReview.svg';

interface Props {
  children: ReactNode;
}

const MyPageTitle = ({ children }: Props) => {
  const matchToInfo = useMatch('/mypage/myInfo');
  const matchToPlan = useMatch('/mypage/myPlan');
  const matchToReview = useMatch('/mypage/myReview');

  return (
    <Title>
      {matchToInfo !== null && <MyInfoIcon />}
      {matchToPlan !== null && <MyPlanIcon />}
      {matchToReview !== null && <MyReviewIcon />}
      {children}
    </Title>
  );
};

export default MyPageTitle;

const Title = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;

  margin-bottom: 20px;

  font-family: 'Pretendard-SemiBold';
  font-size: 20px;
  color: ${(props) => props.theme.colors.blackFont};

  svg {
    width: 30px;
    margin-right: 5px;
  }
`;
