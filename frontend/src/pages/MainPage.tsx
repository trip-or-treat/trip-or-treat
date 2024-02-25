import Banner from 'src/components/main/Banner';
import RegionList from 'src/components/main/RegionList/RegionList';

import styled from 'styled-components';

const MainPage = () => {
  return (
    <Wrapper>
      <Banner />
      <RegionList />
    </Wrapper>
  );
};

export default MainPage;

const Wrapper = styled.div``;
