import styled from 'styled-components';

import MyProfile from 'src/components/mypage/MyProfile';
import MyInfo from 'src/components/mypage/MyInfo';
import MyPageTitle from 'src/components/mypage/MyPageTitle';

import { UserInfo } from 'src/@types/api/userInfo';
import { useUserInfo } from 'src/hooks/api/userInfo';

interface UserData {
  data: {
    data: UserInfo;
  };
  isLoading: boolean;
  isError: boolean;
}

const MyInfoPage = () => {
  const { data: userApi }: UserData = useUserInfo();

  return (
    <Wrapper>
      <MyPageTitle>회원정보수정</MyPageTitle>
      <MyProfile />
      <MyInfo email={userApi?.data.email} nickname={userApi?.data.nickname} />
    </Wrapper>
  );
};

export default MyInfoPage;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
`;
