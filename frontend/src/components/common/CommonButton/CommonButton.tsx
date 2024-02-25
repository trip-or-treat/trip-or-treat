import styled from 'styled-components';
import { ReactNode } from 'react';

type ButtonTextProps = {
  children: ReactNode;
};

const CommonButton = ({ children }: ButtonTextProps) => {
  return <CommonButtonBox>{children}</CommonButtonBox>;
};

export default CommonButton;

const CommonButtonBox = styled.button`
  width: 280px;
  height: 60px;

  color: ${(props) => props.theme.colors.whiteFont};
  background-color: ${(props) => props.theme.colors.mainColor};
  border: none;
  border-radius: 5px;

  font-size: 23px;
  font-family: 'Pretendard-Regular';
`;
