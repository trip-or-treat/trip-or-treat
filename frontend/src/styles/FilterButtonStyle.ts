import { css } from 'styled-components';

export const FilterButtonStyle = css<{ $isClicked: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 10px;

  border-radius: 25px;
  border: none;

  outline: none;
  box-shadow: 0px 3px 3px 0px rgba(0, 0, 0, 0.2);

  background-color: ${(props) =>
    props.$isClicked ? props.theme.colors.mainColor : props.theme.colors.whiteFont};
  color: ${(props) =>
    props.$isClicked ? props.theme.colors.whiteFont : props.theme.colors.blackFont};

  font-size: 12px;
  font-family: 'Pretendard-Regular';

  cursor: pointer;
`;
