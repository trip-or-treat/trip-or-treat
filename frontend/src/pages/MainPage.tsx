import { useEffect } from 'react';
import { useRecoilState } from 'recoil';

import Banner from 'src/components/main/Banner';
import RegionList from 'src/components/main/RegionList';
import FloattingBtn from 'src/components/main/FloattingBtn';

import homeModalAtom from 'src/atoms/homeModalAtom';
import useInitData from 'src/hooks/useInitData';

const MainPage = () => {
  const [isModal, setIsModal] = useRecoilState(homeModalAtom);

  useInitData();

  useEffect(() => {
    setIsModal(false);
  }, [isModal]);

  return (
    <div>
      <FloattingBtn />
      <Banner />
      <RegionList />
    </div>
  );
};

export default MainPage;
