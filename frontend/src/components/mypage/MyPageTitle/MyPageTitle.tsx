import { ReactNode } from 'react';
import styled from 'styled-components';

import { ReactComponent as MyInfoIcon } from '../../../assets/svgs/myInfo.svg';
import { ReactComponent as MyPlanIcon } from '../../../assets/svgs/myPlan.svg';
import { ReactComponent as MyReviewIcon } from '../../../assets/svgs/myReview.svg';

interface Props {
  children: ReactNode;
}

const MyPageTitle = ({ children }: Props) => {
  return (
    <Title>
      {children === '회원정보수정' && <MyInfoIcon />}
      {children === '내 여행계획' && <MyPlanIcon />}
      {children === '내 리뷰 목록' && <MyReviewIcon />}
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
