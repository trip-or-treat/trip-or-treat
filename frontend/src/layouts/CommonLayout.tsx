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
      {isModal && (
        <AlertModal path="/" onButtonText="홈으로" offButtonText="닫기" onClose={onClose}>
          홈으로 돌아가시겠습니까?
        </AlertModal>
      )}
    </>
  );
};

export default CommonLayout;
