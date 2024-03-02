import Banner from 'src/components/main/Banner';
import RegionList from 'src/components/main/RegionList/RegionList';
import SearchText from 'src/components/main/Search';

import styled from 'styled-components';

const MainPage = () => {
  return (
    <Wrapper>
      <Banner />
      <SearchText />
      <RegionList />
    </Wrapper>
  );
};

export default MainPage;

const Wrapper = styled.div``;
