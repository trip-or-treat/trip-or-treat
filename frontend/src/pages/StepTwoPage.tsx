import styled from 'styled-components';
import { useState } from 'react';

import EnterSearch from 'src/components/EnterSearch';
import MyRegionList from 'src/components/MyRegionList';
import SearchRegionList from 'src/components/SearchRegionList';

const StepTwoPage = () => {
  const [keyword, setKeyword] = useState('');

  return (
    <Wrapper>
      <SearchLayer>
        <MyRegionList />

        <section>
          <EnterSearch placeHolder="여행지를 검색해보세요!" setKeyword={setKeyword} />
        </section>

        <SearchRegionList keyword={keyword} />
      </SearchLayer>

      <MapLayer>mapLayer</MapLayer>
    </Wrapper>
  );
};

export default StepTwoPage;

const Wrapper = styled.div`
  display: flex;

  width: calc(100vw - ${(props) => props.theme.width.leftNavWidth});
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;

const SearchLayer = styled.div`
  width: 30%;
  height: inherit;
  border-left: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};
  border-right: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};

  box-sizing: border-box;

  section {
    padding: 0px 20px;
  }
`;

const MapLayer = styled.div`
  width: 70%;
  height: inherit;

  background-color: darkgoldenrod;
`;
