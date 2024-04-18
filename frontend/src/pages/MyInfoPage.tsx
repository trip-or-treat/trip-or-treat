import styled from 'styled-components';

import MyProfile from 'src/components/mypage/MyProfile';
import MyInfo from 'src/components/mypage/MyInfo';
import MyPageTitle from 'src/components/mypage/MyPageTitle';

import { UserInfo } from 'src/@types/api/userInfo';
import { useUserInfo } from 'src/hooks/api/userInfo';
import Loading from 'src/components/common/Loading';

interface UserData {
  data: {
    data: UserInfo;
  };
  isLoading: boolean;
  isError: boolean;
}

const MyInfoPage = () => {
  const { data: userApi, isLoading }: UserData = useUserInfo();

  return !isLoading ? (
    <Wrapper>
      <MyPageTitle>회원정보수정</MyPageTitle>
      <MyProfile imageThumbnail={userApi?.data.imageThumbnail} />
      <MyInfo email={userApi?.data.email} nickname={userApi?.data.nickname} />
    </Wrapper>
  ) : (
    <Loading type="LARGE" />
  );
};

export default MyInfoPage;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
`;
