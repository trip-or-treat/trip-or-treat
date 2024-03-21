import React, { Children } from 'react';
import styled from 'styled-components';

interface Props {
  curScreenY: number;
  isOpenMemo: boolean;
  children: React.ReactNode;
}

interface ContentsWrapperProps {
  $curScreenY: number;
  $isOpenMemo: boolean;
}

const Accordion = ({ curScreenY, isOpenMemo, children }: Props) => {
  const [header, contents] = Children.toArray(children);

  return (
    <Container>
      <Header>{header}</Header>
      <ContentsWrapper $curScreenY={curScreenY} $isOpenMemo={isOpenMemo}>
        <Contents>{contents}</Contents>
      </ContentsWrapper>
    </Container>
  );
};

export default Accordion;

const Container = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
`;

const ContentsWrapper = styled.div<ContentsWrapperProps>`
  overflow: hidden;
  position: absolute;
  z-index: 5;
  top: ${(props) => props.$curScreenY < 630 && '60px'};
  bottom: ${(props) => props.$curScreenY > 630 && '60px'};

  height: ${(props) => (props.$isOpenMemo ? '200px' : '0px')};
  width: 100%;

  border: ${(props) => (props.$isOpenMemo ? '1px solid rgba(0, 0, 0, 0.3)' : 'none')};
  border-radius: 5px;

  background-color: white;
  transition: height 0.35s ease;
`;

const Contents = styled.div``;
