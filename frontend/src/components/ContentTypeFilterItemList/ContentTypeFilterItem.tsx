import React from 'react';
import { useRecoilState } from 'recoil';
import styled from 'styled-components';

import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';

interface Props {
  id: number;
  title: string;
}

const ContentTypeFilterItem = ({ id, title }: Props) => {
  const [prevContentTypeId, setPrevContentTypeId] = useRecoilState(contentTypeIdAtom);

  const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    const currentId = Number(e.currentTarget.dataset.id) ?? null;

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

export default ContentTypeFilterItem;

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
