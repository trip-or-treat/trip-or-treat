import { Link, useLocation } from 'react-router-dom';
import { styled, css } from 'styled-components';
import { useSetRecoilState, useRecoilValue } from 'recoil';

import dateSelectStateAtom from 'src/atoms/dateSelectStateAtom';
import myRegionListAtom from 'src/atoms/myRegionListAtom';

import StepNavLinkButton from './StepNavLinkButton';

const STEP_NAV_DATA = [
  { step: 1, content: '날짜 선택', path: 'date' },
  { step: 2, content: '지역 추가', path: 'region' },
  { step: 3, content: '계획 짜기', path: 'place' },
];

const StepNavBar = () => {
  const myRegionList = useRecoilValue(myRegionListAtom);
  const curRegionId = myRegionList[0]?.id;
  const { pathname } = useLocation();
  const [currentPath] = pathname.split('/').slice(1);
  const curStep = STEP_NAV_DATA.find((data) => data.path === currentPath)?.step;
  const setDateSelect = useSetRecoilState(dateSelectStateAtom);

  const onClick = () => {
    if (curStep === 2 || curStep === 3) {
      setDateSelect(true);
    }
  };

  return (
    <Nav>
      {STEP_NAV_DATA.map((item, idx) => (
        <NavItem key={item.step}>
          {idx === 0 ? (
            <ExtendBox $isClicked={idx + 1 === curStep} onClick={onClick}>
              <LinkItem>{`step${idx + 1}`}</LinkItem>
              <LinkItem>{item.content}</LinkItem>
            </ExtendBox>
          ) : (
            <ExtendLinkBox to={`${item.path}/${curRegionId}`} $isClicked={idx + 1 === curStep}>
              <LinkItem>{`step${idx + 1}`}</LinkItem>
              <LinkItem>{item.content}</LinkItem>
            </ExtendLinkBox>
          )}
        </NavItem>
      ))}

      <ButtonBox>
        {curStep === 2 && (
          <StepNavLinkButton type="ENABLE_ONLY" path={`/place/${curRegionId}`}>
            다음
          </StepNavLinkButton>
        )}

        {curStep === 3 && (
          <StepNavLinkButton type="ENABLE_AND_DISABLE" path="/plans">
            계획 저장
          </StepNavLinkButton>
        )}
      </ButtonBox>
    </Nav>
  );
};

export default StepNavBar;

const Nav = styled.ul`
  display: flex;
  flex-direction: column;
  align-items: center;
  position: fixed;

  margin-top: ${(props) => props.theme.height.topNavHeight};

  width: ${(props) => props.theme.width.leftNavWidth};
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});

  background-color: ${(props) => props.theme.colors.whiteFont};
`;

const NavItem = styled.li`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  margin: 30px 0px;
`;

const ButtonBox = styled.div`
  position: absolute;
  bottom: 50px;
`;

const stepInner = css<{ $isClicked: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  color: ${(props) =>
    props.$isClicked ? props.theme.colors.mainColor : props.theme.colors.darkGrey};
  text-decoration: none;
  font-family: 'Pretendard-SemiBold';

  transition: transform 0.25s ease-in;
  transform: ${(props) => (props.$isClicked ? 'scale(1.1)' : 'scale(1)')};
`;

const LinkItem = styled.p`
  margin: 5px 0px;
`;

const ExtendLinkBox = styled(Link)<{ $isClicked: boolean }>`
  ${stepInner}
`;

const ExtendBox = styled.div<{ $isClicked: boolean }>`
  ${stepInner}
  cursor: pointer;
`;
