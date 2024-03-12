import { useSetRecoilState } from 'recoil';
import { Link, useLocation } from 'react-router-dom';
import { styled, css } from 'styled-components';
import dateSelectStateAtom from 'src/atoms/dateSelectStateAtom';
import StepNavLinkButton from './StepNavLinkButton';

const STEP_NAV_DATA = [
  { step: 1, content: '날짜 선택', path: 'date' },
  { step: 2, content: '지역 추가', path: 'region' },
  { step: 3, content: '계획 짜기', path: 'place' },
];

const StepNavBar = () => {
  const { pathname } = useLocation();
  const [currentPath, regionId] = pathname.split('/').slice(1);
  const currentStep = STEP_NAV_DATA.find((item) => item.path === currentPath)?.step;
  const setDateSelect = useSetRecoilState(dateSelectStateAtom);

  const onClick = () => {
    if (currentStep === 2 || currentStep === 3) {
      setDateSelect(true);
    }
  };

  return (
    <Nav>
      {STEP_NAV_DATA.map((item, idx) => (
        <NavItem key={item.step}>
          {idx === 0 ? (
            <ExtendBox $isClicked={idx + 1 === currentStep} onClick={onClick}>
              <LinkItem>{`step${idx + 1}`}</LinkItem>
              <LinkItem>{item.content}</LinkItem>
            </ExtendBox>
          ) : (
            <ExtendLinkBox to={`${item.path}/${regionId}`} $isClicked={idx + 1 === currentStep}>
              <LinkItem>{`step${idx + 1}`}</LinkItem>
              <LinkItem>{item.content}</LinkItem>
            </ExtendLinkBox>
          )}
        </NavItem>
      ))}

      <ButtonBox>
        {currentStep === 2 && (
          <StepNavLinkButton type="ENABLE_ONLY" path={`/place/${regionId}`}>
            다음
          </StepNavLinkButton>
        )}

        {currentStep === 3 && (
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
`;
