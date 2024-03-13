import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import KaKaoMap from 'src/components/KaKaoMap';
import RegionCategory from 'src/components/RegionCategoryList/RegionCategoryList';
import EnterSearch from 'src/components/EnterSearch';
import PlaceList from 'src/components/PlaceList';
import DayCategory from 'src/components/DayCategory';
import ContentTypeFilterItemList from 'src/components/ContentTypeFilterItemList';
import SelectedPlaceCardList from 'src/components/SelectedPlaceCardList';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import totalPlanAtom from 'src/atoms/totalPlanAtom';
import stepPlanSavedBtnAtom from 'src/atoms/stepPlanSavedBtnAtom';
import curDayAtom from 'src/atoms/curDayAtom';
import { PlaceListTypes } from 'src/@types/api/placeList';
import FailDataPage from './FailDataPage';

const StepThreePage = () => {
  const [keyword, setKeyword] = useState('');
  const [selectedPlaceList, setSelectedPlaceList] = useState<PlaceListTypes[]>([]);

  const myRegionList = useRecoilValue(myRegionListAtom);
  const totalPlan = useRecoilValue(totalPlanAtom);
  const setStepPlanSaveBtn = useSetRecoilState(stepPlanSavedBtnAtom);
  const curDay = useRecoilValue(curDayAtom);

  useEffect(() => {
    setStepPlanSaveBtn(totalPlan.map((data) => data?.items).every((data) => data.length !== 0));
  }, [totalPlan]);

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
      <SearchLayer>
        <section>
          <RegionCategory data={myRegionList} />
          <EnterSearch placeHolder="장소를 검색해보세요!" setKeyword={setKeyword} />
          <ContentTypeFilterItemList />
        </section>

        <PlaceList keyword={keyword} setKeyword={setKeyword} />
      </SearchLayer>

      <DayLayer>
        <DayCategory />
        <SelectedPlaceCardList />
      </DayLayer>

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
  width: 27%;
  height: inherit;
  padding: 20px;

  box-sizing: border-box;
`;

const MapLayer = styled.div`
  width: 48%;
  height: inherit;
`;
