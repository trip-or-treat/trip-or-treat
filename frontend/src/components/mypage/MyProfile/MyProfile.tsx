import styled from 'styled-components';

import DefaultProfile from '../../../assets/images/ghost.png';

// TODO : 프로필 변경 버튼 누르면 사진 수정

const MyProfile = () => {
  return (
    <ProfileBox>
      <ProfileImg src={DefaultProfile} />
      <ChangeProfile>프로필 변경</ChangeProfile>
    </ProfileBox>
  );
};

export default MyProfile;

const ProfileBox = styled.div`
  display: flex;
  flex-direction: column;
  text-align: center;
  justify-content: center;
  margin: 0 auto 20px auto;
`;

const ProfileImg = styled.img<{ src: string }>`
  width: 130px;
  height: 130px;
  padding: 10px;
  border: solid 0.7px darkGray;
  border-radius: 50%;
`;

const ChangeProfile = styled.button`
  width: 100px;
  height: 25px;
  margin: 20px auto 0 auto;

  border: none;
  background-color: white;

  font-family: 'Pretendard-Regular';
  font-size: 15px;
  color: #3d6eff;

  cursor: pointer;

  &:hover {
    color: #0741ed;
  }
`;
