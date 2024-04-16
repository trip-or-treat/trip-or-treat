import { useRef } from 'react';
import media from 'src/styles/media';
import styled from 'styled-components';

const StartButton = () => {
  const searchRef = useRef<HTMLDivElement>(null);

  const onStartBtnClick = () => {
    searchRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  return (
    <Wrapper>
      <StartButtonBox onClick={onStartBtnClick}>시작하기</StartButtonBox>
      <Scroll ref={searchRef} />
    </Wrapper>
  );
};

export default StartButton;

const Wrapper = styled.div`
  ${media.mobile`
    height: 300px;
  `}
`;

const StartButtonBox = styled.button`
  display: block;

  width: 340px;
  height: 55px;
  margin: auto auto 130px auto;

  background-color: ${(props) => props.theme.colors.mainColor};
  border: none;
  border-radius: 5px;

  font-size: 20px;
  font-family: 'Pretendard-SemiBold';
  color: ${(props) => props.theme.colors.whiteFont};
  text-align: center;
  text-decoration: none;

  cursor: pointer;
  &:hover {
    background-color: ${(props) => props.theme.colors.hoverColor};
  }

  ${media.mobile`
    width: 350px;
    height: 65px;
    margin: margin: auto auto 100px auto;

    font-size: 25px;
  `}
`;

const Scroll = styled.div`
  height: ${(props) => props.theme.height.topNavHeight};

  ${media.mobile`
    height: 260px;
  `}
`;
