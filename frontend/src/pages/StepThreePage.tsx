import { useState } from 'react';
import styled from 'styled-components';
import { useRecoilValue } from 'recoil';

import RegionCategory from 'src/components/RegionCategory';
import EnterSearch from 'src/components/EnterSearch';
import DayCategory from 'src/components/DayCategory';
import ContentTypeFilterItemList from 'src/components/ContentTypeFilterItemList';

import myRegionListAtom from 'src/atoms/myRegionListAtom';

const StepThreePage = () => {
  const [curDay, setCurDay] = useState(1);
  const [keyword, setKeyword] = useState('');
  const myRegionList = useRecoilValue(myRegionListAtom);

  return (
    <Wrapper>
      <SearchLayer>
        <section>
          <RegionCategory data={myRegionList} />
          <EnterSearch placeHolder="장소를 검색해보세요!" setKeyword={setKeyword} />
          <ContentTypeFilterItemList />
        </section>
      </SearchLayer>

      <DayLayer>
        <DayCategory curDay={curDay} setCurDay={setCurDay} />
      </DayLayer>
      <MapLayer>mapLayer</MapLayer>
    </Wrapper>
  );
};

export default StepThreePage;

const Wrapper = styled.div`
  display: flex;

  width: calc(100vw - ${(props) => props.theme.width.leftNavWidth});
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;

const SearchLayer = styled.div`
  width: 25%;
  height: inherit;
  border-left: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};
  border-right: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};

  box-sizing: border-box;

  & > section {
    padding: 20px;
  }
`;

const DayLayer = styled.div`
  width: 30%;
  height: inherit;
  padding: 20px;

  box-sizing: border-box;
`;

const MapLayer = styled.div`
  width: 45%;
  height: inherit;

  background-color: darkgoldenrod;
`;
