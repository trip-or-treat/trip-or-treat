import styled from 'styled-components';

import MyProfile from 'src/components/mypage/MyProfile';
import MyInfo from 'src/components/mypage/MyInfo';
import MyPageTitle from 'src/components/mypage/MyPageTitle';

const MyInfoPage = () => {
  return (
    <Wrapper>
      <MyPageTitle>회원정보수정</MyPageTitle>
      <MyProfile />
      <MyInfo />
    </Wrapper>
  );
};

export default MyInfoPage;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
`;
