import styled from 'styled-components';

import ghostImg from '../assets/images/ghost.png';

const FailDataPage = () => {
  return (
    <EmptyBox>
      <p>데이터를 불러오던 중 실패했습니다</p>
      <img src={ghostImg} alt="고스트 이미지" />
    </EmptyBox>
  );
};

export default FailDataPage;

const EmptyBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});

  p {
    font-size: 3rem;
    font-family: 'Pretendard-Medium';
    color: ${(props) => props.theme.colors.blackFont};
    margin-bottom: 50px;
  }

  img {
    width: 13rem;
  }
`;
