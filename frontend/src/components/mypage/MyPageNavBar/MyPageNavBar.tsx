import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

const MyPageNavBar = () => {
  return (
    <NavBox>
      <NavItem to="/mypage/myInfo">회원정보수정</NavItem>
      <NavItem to="/mypage/myPlan">내 여행계획</NavItem>
      <NavItem to="/mypage/myReview">내 리뷰 목록</NavItem>
    </NavBox>
  );
};

export default MyPageNavBar;

const NavBox = styled.div`
  position: fixed;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  margin-left: 15%;
  width: 170px;
  height: 150px;

  border: solid 1px lightGray;
  border-radius: 10px;
`;

const NavItem = styled(NavLink)`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 170px;
  height: 50px;

  border-radius: 10px;

  text-decoration: none;
  font-family: 'Pretendard-Medium';
  font-size: 16px;
  color: ${(props) => props.theme.colors.blackFont};

  cursor: pointer;

  &.active {
    background-color: ${(props) => props.theme.colors.mainColor};
    color: ${(props) => props.theme.colors.whiteFont};
  }
`;
