import { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import styled from 'styled-components';

import Banner from 'src/components/main/Banner';
import RegionList from 'src/components/main/RegionList';

import homeModalAtom from 'src/atoms/homeModalAtom';
import useInitData from 'src/hooks/useInitData';

const MainPage = () => {
  const [isModal, setIsModal] = useRecoilState(homeModalAtom);

  useInitData();

  useEffect(() => {
    setIsModal(false);
  }, [isModal]);

  return (
    <Wrapper>
      <Banner />
      <RegionList />
    </Wrapper>
  );
};

export default MainPage;

const Wrapper = styled.div``;
