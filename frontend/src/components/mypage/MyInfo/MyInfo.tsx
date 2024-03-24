import styled from 'styled-components';

import { ReactComponent as EditIcon } from '../../../assets/svgs/edit.svg';

// TODO: 닉네임 변경

const MyInfo = () => {
  return (
    <Info>
      <MenuBox>
        <InfoMenu>닉네임</InfoMenu>
        <InfoMenu>이름</InfoMenu>
        <InfoMenu>이메일</InfoMenu>
      </MenuBox>
      <ContentBox>
        <InfoContent>
          여행자1
          <EditIcon />
        </InfoContent>
        <InfoContent>고스트</InfoContent>
        <InfoContent>triportreat@gmail.com</InfoContent>
      </ContentBox>
    </Info>
  );
};

export default MyInfo;

const Info = styled.div`
  display: flex;
  flex-direction: row;

  width: 700px;
  height: 250px;

  border: solid 1px darkGray;
  border-radius: 15px;

  font-family: 'Pretendard-Regular';
  font-size: 17px;
`;

const MenuBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;

  width: 30%;
`;

const InfoMenu = styled.div`
  margin-left: 100px;

  line-height: 60px;
`;

const ContentBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;

  width: 70%;
`;

const InfoContent = styled.div`
  display: flex;
  align-items: center;

  margin-left: 30px;

  line-height: 60px;

  svg {
    margin-left: 25px;

    cursor: pointer;

    &:hover {
      width: 15px;
      height: 15px;
      background-color: lightGrey;
      border: none;
      border-radius: 3px;
    }
  }
`;
