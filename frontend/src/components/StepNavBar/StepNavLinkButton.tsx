import React from 'react';
import { useRecoilValue } from 'recoil';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

import stepPlanSavedBtnAtom from 'src/atoms/stepPlanSavedBtnAtom';

interface Props {
  path: string;
  type: 'ENABLE_ONLY' | 'ENABLE_AND_DISABLE';
  children: React.ReactNode;
}

const StepNavLinkButton = ({ path, type, children }: Props) => {
  const isClicked = useRecoilValue(stepPlanSavedBtnAtom);

  return (
    <Wrapper $isClicked={isClicked}>
      {type === 'ENABLE_ONLY' && <LinkBox to={path}>{children}</LinkBox>}
      {type === 'ENABLE_AND_DISABLE' && (
        <ChangeLinkBox to={isClicked ? path : '#'} $isClicked={isClicked}>
          {children}
        </ChangeLinkBox>
      )}
    </Wrapper>
  );
};

export default StepNavLinkButton;

const Wrapper = styled.div<{ $isClicked: boolean }>`
  cursor: ${(props) => (props.$isClicked ? 'pointer' : 'not-allowed')};
`;

const LinkBox = styled(Link)`
  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 50px;
  max-width: 80px;

  padding: 25px 20px;

  border-radius: 8px;
  border: none;

  background-color: ${(props) => props.theme.colors.mainColor};
  color: ${(props) => props.theme.colors.whiteFont};

  font-size: 14px;
  text-decoration: none;
  cursor: pointer;
`;

const ChangeLinkBox = styled(LinkBox)<{ $isClicked: boolean }>`
  background-color: ${(props) =>
    props.$isClicked ? props.theme.colors.mainColor : props.theme.colors.darkGrey};
  pointer-events: ${(props) => (props.$isClicked ? props.theme.colors.mainColor : 'none')};
`;
