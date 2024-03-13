import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import { useRecoilState } from 'recoil';

import Nav from 'src/components/common/Nav';
import StepNavBar from 'src/components/StepNavBar';
import AlertModal from 'src/components/AlertModal';

import homeModalAtom from 'src/atoms/homeModalAtom';

const StepLayout = () => {
  const [isModal, setIsModal] = useRecoilState(homeModalAtom);

  const onClose = () => {
    setIsModal(false);
  };

  return (
    <>
      <Nav />
      <StepNavBar />
      <Main>
        <Outlet />
      </Main>
      {isModal && <AlertModal onClose={onClose} />}
    </>
  );
};

export default StepLayout;

const Main = styled.main`
  position: fixed;
  margin-top: ${(props) => props.theme.height.topNavHeight};
  margin-left: ${(props) => props.theme.width.leftNavWidth};
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;
