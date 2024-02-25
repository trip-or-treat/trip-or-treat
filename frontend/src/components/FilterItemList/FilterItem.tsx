import React from 'react';
import styled from 'styled-components';

interface Props {
  id: string;
  title: string;
  prevContentTypeId: string | null;
  setPrevContentTypeId: React.Dispatch<React.SetStateAction<string | null>>;
}

const FilterItem = ({ id, title, prevContentTypeId, setPrevContentTypeId }: Props) => {
  const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    const currentId = e.currentTarget.dataset.id ?? null;

    if (prevContentTypeId === currentId) {
      setPrevContentTypeId(null);
    } else {
      setPrevContentTypeId(currentId);
    }
  };

  return (
    <Button onClick={handleClick} $isClicked={id === prevContentTypeId ?? false} data-id={id}>
      {title}
    </Button>
  );
};

export default FilterItem;

const Button = styled.button<{ $isClicked: boolean }>`
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
  cursor: pointer;
`;
