import styled from 'styled-components';

// TODO
// 버튼 클릭 시 스크롤 기능

const StartButton = () => {
  return <StartButtonBox>시작하기</StartButtonBox>;
};

export default StartButton;

const StartButtonBox = styled.button`
  display: block;

  width: 370px;
  height: 65px;
  margin: auto;

  background-color: ${(props) => props.theme.colors.mainColor};
  border: none;
  border-radius: 5px;

  font-size: 25px;
  font-family: 'Pretendard-Regular';
  color: ${(props) => props.theme.colors.whiteFont};
  text-align: center;
  text-decoration: none;

  cursor: pointer;
`;
