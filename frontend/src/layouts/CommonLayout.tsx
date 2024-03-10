import { Outlet } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import homeModalAtom from 'src/atoms/homeModalAtom';
import AlertModal from 'src/components/AlertModal';
import Nav from 'src/components/common/Nav';

const CommonLayout = () => {
  const [isModal, setIsModal] = useRecoilState(homeModalAtom);

  const onClose = () => {
    setIsModal(false);
  };

  return (
    <>
      <Nav />
      <Outlet />
      {isModal && <AlertModal onClose={onClose} />}
    </>
  );
};

export default CommonLayout;
