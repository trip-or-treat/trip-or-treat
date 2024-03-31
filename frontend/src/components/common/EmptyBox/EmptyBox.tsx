import styled from 'styled-components';
import { ReactNode } from 'react';

import GhostImg from '../../../assets/images/ghost.png';

interface Props {
  children: ReactNode;
}
const EmptyBox = ({ children }: Props) => {
  return (
    <EmptyItemWrapper>
      <p>{children}</p>
      <img src={GhostImg} alt="고스트 이미지" />
    </EmptyItemWrapper>
  );
};

export default EmptyBox;

const EmptyItemWrapper = styled.div`
  display: flex;
  flex-direction: column;

  margin-top: 90px;

  justify-content: center;
  align-items: center;

  font-family: 'Pretendard-Medium';
  font-size: 17px;
  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-Medium';

  img {
    width: 75px;
    height: 75px;
    margin-top: 20px;
  }
`;
