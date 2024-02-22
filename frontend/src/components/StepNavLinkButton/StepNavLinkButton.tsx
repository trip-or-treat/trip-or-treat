import React from 'react';
import { useRecoilValue } from 'recoil';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import stepPlanSavedBtn from 'src/atoms/stepPlanSavedBtn';

interface Props {
  path: string;
  type: 'CHANGE' | 'NOT_CHANGE';
  children: React.ReactNode;
}

const StepNavLinkButton = ({ path, type, children }: Props) => {
  const isClicked = useRecoilValue(stepPlanSavedBtn);

  return (
    <Wrapper $isClicked={isClicked}>
      {type === 'NOT_CHANGE' && <LinkBox to={path}>{children}</LinkBox>}
      {type === 'CHANGE' && (
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

  border: none;
  padding: 25px 20px;
  border-radius: 8px;

  background-color: ${(props) => props.theme.colors.mainColor};
  color: ${(props) => props.theme.colors.whiteFont};

  font-size: 14px;
  cursor: pointer;
  text-decoration: none;
`;

const ChangeLinkBox = styled(LinkBox)<{ $isClicked: boolean }>`
  background-color: ${(props) =>
    props.$isClicked ? props.theme.colors.mainColor : props.theme.colors.darkGrey};
  pointer-events: ${(props) => (props.$isClicked ? props.theme.colors.mainColor : 'none')};
`;
