import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useRecoilValue } from 'recoil';

import KaKaoMap from 'src/components/KaKaoMap';
import RegionCategory from 'src/components/RegionCategoryList/RegionCategoryList';
import EnterSearch from 'src/components/EnterSearch';
import PlaceList from 'src/components/PlaceList';
import DayCategory from 'src/components/DayCategory';
import ContentTypeFilterItemList from 'src/components/ContentTypeFilterItemList';
import SelectedPlaceCardList from 'src/components/SelectedPlaceCardList';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import totalPlanAtom from 'src/atoms/totalPlanAtom';

import curDayAtom from 'src/atoms/curDayAtom';
import { PlaceListTypes } from 'src/@types/api/placeList';

import media from 'src/styles/media';
import FailDataPage from './FailDataPage';

const StepThreePage = () => {
  const [keyword, setKeyword] = useState('');
  const [selectedPlaceList, setSelectedPlaceList] = useState<PlaceListTypes[]>([]);

  const myRegionList = useRecoilValue(myRegionListAtom);
  const totalPlan = useRecoilValue(totalPlanAtom);
  const curDay = useRecoilValue(curDayAtom);

  useEffect(() => {
    setSelectedPlaceList(totalPlan[curDay - 1]?.items);
  }, [curDay, totalPlan]);

  if (!totalPlan[0] || !myRegionList)
    return (
      <Wrapper>
        <FailDataPage />
      </Wrapper>
    );

  return (
    <Wrapper>
      <PlaceBox>
        <SearchLayer>
          <section>
            <RegionCategory data={myRegionList} type="LINK" />
            <EnterSearch placeHolder="장소를 검색해보세요!" setKeyword={setKeyword} />
            <ContentTypeFilterItemList />
          </section>

          <PlaceList keyword={keyword} setKeyword={setKeyword} />
        </SearchLayer>

        <DayLayer>
          <DayCategory />
          <SelectedPlaceCardList />
        </DayLayer>
      </PlaceBox>

      <MapLayer>
        <KaKaoMap list={selectedPlaceList} curDay={curDay} />
      </MapLayer>
    </Wrapper>
  );
};

export default StepThreePage;

const Wrapper = styled.div`
  display: flex;

  width: calc(100vw - ${(props) => props.theme.width.leftNavWidth});
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});

  ${media.mobile`
  flex-direction: column;  
  `}
`;

const PlaceBox = styled.div`
  display: flex;
  width: 50%;

  ${media.mobile`
  width: 100%;  
  max-height:50rem;
  overflow: auto;
  margin-bottom:20px;
  `}
`;

const SearchLayer = styled.div`
  width: 50%;
  height: inherit;
  border-left: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};
  border-right: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};

  box-sizing: border-box;

  & > section {
    padding: 20px;
  }

  ${media.mobile`
  width: 50%;  
  max-height:50rem;
`}
`;

const DayLayer = styled.div`
  width: 50%;
  height: 100vh;
  padding: 20px;

  box-sizing: border-box;

  ${media.mobile`
  width: 50%;  
  max-height:50rem;
`}
`;

const MapLayer = styled.div`
  width: 48%;
  height: inherit;

  ${media.mobile`
  width:100%;
  height:50rem;
`}
`;
