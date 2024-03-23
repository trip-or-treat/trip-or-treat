import React from 'react';
import { useRecoilState } from 'recoil';
import styled from 'styled-components';

import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';
import { FilterButtonStyle } from '../common/FilterButtonStyle';

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
  ${FilterButtonStyle};

  background-color: ${(props) =>
    props.$isClicked ? props.theme.colors.mainColor : props.theme.colors.whiteFont};
  color: ${(props) =>
    props.$isClicked ? props.theme.colors.whiteFont : props.theme.colors.blackFont};
`;
