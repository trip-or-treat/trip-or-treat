import styled from 'styled-components';
import RegionCategory from 'src/components/RegionCategory';
import EnterSearch from 'src/components/EnterSearch';
import ContentTypeFilterItemList from 'src/components/ContentTypeFilterItemList';
import { useState } from 'react';
import DayCategory from 'src/components/DayCategory copy';
import { useRecoilValue } from 'recoil';
import myRegionListAtom from 'src/atoms/myRegionListAtom';

const StepThreePage = () => {
  const [curDay, setCurDay] = useState(1);
  const myRegionList = useRecoilValue(myRegionListAtom);

  return (
    <Wrapper>
      <SearchLayer>
        <RegionCategory data={myRegionList} />
        <EnterSearch placeHolder="장소를 검색해보세요!" />
        <ContentTypeFilterItemList />
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
  padding: 20px;
  border-left: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};
  border-right: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};

  box-sizing: border-box;
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
