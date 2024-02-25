import styled from 'styled-components';

const StartButton = () => {
  return <StartButtonBox>시작하기</StartButtonBox>;
};

export default StartButton;

const StartButtonBox = styled.button`
  display: block;

  width: 300px;
  height: 60px;
  margin: auto;

  background-color: ${(props) => props.theme.colors.mainColor};
  border: none;
  border-radius: 5px;

  font-size: 30px;
  font-family: 'Pretendard-Regular';
  color: ${(props) => props.theme.colors.whiteFont};
  text-align: center;
  text-decoration: none;
`;
