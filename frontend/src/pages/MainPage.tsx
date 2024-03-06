import Banner from 'src/components/main/Banner';
import RegionList from 'src/components/main/RegionList';
import Search from 'src/components/main/Search';

import styled from 'styled-components';

const MainPage = () => {
  return (
    <Wrapper>
      <Banner />
      <Search />
      <RegionList />
    </Wrapper>
  );
};

export default MainPage;

const Wrapper = styled.div``;
