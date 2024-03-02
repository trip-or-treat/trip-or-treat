import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { useRecoilValue } from 'recoil';

import overviewTitle from 'src/atoms/overviewTitle';
import DefaultView from './DefaultView';

interface Props {
  children: React.ReactNode;
}

const Overview = ({ children }: Props) => {
  const isTitle = useRecoilValue(overviewTitle);
  const [moreText, setMoreText] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  useEffect(() => {
    if (typeof children === 'string') {
      if (children.length > 248) setMoreText(true);
      if (children.length === 0) setIsEmpty(true);
    }
  }, []);

  const closeMoreText = () => {
    setMoreText(false);
  };

  return (
    <>
      {isTitle && <StyledTitle>개요</StyledTitle>}
      <StyledDescription $isMore={moreText}>
        {isEmpty && <DefaultView />}
        {!isEmpty && children}
        {moreText && <StyledMoreToggle onClick={closeMoreText}>...더보기</StyledMoreToggle>}
      </StyledDescription>
    </>
  );
};

export default Overview;

const StyledDescription = styled.p<{ $isMore: boolean }>`
  position: relative;
  overflow: ${({ $isMore }) => ($isMore ? 'hidden' : 'auto')};

  width: 455px;
  height: 142px;

  text-overflow: ellipsis;
  word-break: break-all;
  white-space: pre-wrap;
  line-height: 1.5;
  font-family: 'Pretendard-Regular';
`;

const StyledMoreToggle = styled.button`
  position: absolute;

  right: 0;
  bottom: 0;

  background-color: white;
  border: none;

  font-family: 'Pretendard-Thin';

  cursor: pointer;
`;

const StyledTitle = styled.h3`
  width: 455px;
  margin-bottom: 15px;

  font-family: 'Pretendard-Bold';
`;