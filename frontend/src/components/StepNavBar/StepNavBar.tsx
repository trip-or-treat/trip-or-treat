import { Link, useLocation } from 'react-router-dom';
import styled from 'styled-components';
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

  return (
    <Nav>
      {STEP_NAV_DATA.map((item, idx) => (
        <NavItem key={item.step}>
          <LinkBox to={`${item.path}/${regionId}`} $isClicked={idx + 1 === currentStep}>
            <LinkItem>{`step${idx + 1}`}</LinkItem>
            <LinkItem>{item.content}</LinkItem>
          </LinkBox>
        </NavItem>
      ))}

      <ButtonBox>
        {currentStep === 2 && (
          <StepNavLinkButton type="ENABLE_ONLY" path={`/place/${regionId}`}>
            다음
          </StepNavLinkButton>
        )}

        {currentStep === 3 && (
          <StepNavLinkButton type="ENABLE_AND_DISABLE" path="/result">
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

  width: ${(props) => props.theme.width.leftNavWidth};
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});

  border-right: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};
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
  bottom: 100px;
`;

const LinkBox = styled(Link)<{ $isClicked: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  text-decoration: none;
  color: ${(props) =>
    props.$isClicked ? props.theme.colors.mainColor : props.theme.colors.darkGrey};
`;

const LinkItem = styled.p`
  margin: 5px 0px;
`;
