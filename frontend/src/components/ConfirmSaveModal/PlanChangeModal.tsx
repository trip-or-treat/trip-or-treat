import theme from 'src/styles/theme';
import styled from 'styled-components';
import { useRecoilValue } from 'recoil';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';

import curDayAtom from 'src/atoms/curDayAtom';
import { planTitleAtom } from 'src/atoms/plan';
import { totalPlanDetailAtom } from 'src/atoms/planDetail';

import { PlaceListTypes } from 'src/@types/api/placeList';
import { SchedulesType } from 'src/@types/api/schedules';
import { SchedulesTypeWithRegions } from 'src/@types/api/planDetail';

import { usePatchTotalPlan } from 'src/hooks/api/usePatchTotalPlan';

import CloseButton from '../common/modal/CloseButton';
import ModalOverlay from '../common/modal/ModalOverlay';

import KaKaoMap from '../KaKaoMap';
import PlaceList from '../PlaceList';
import PlanTitle from '../plan/PlanNavBar/PlanTitle';
import EnterSearch from '../EnterSearch';
import DayCategory from '../DayCategory';
import RegionCategoryList from '../RegionCategoryList';
import SelectedPlaceCardList from '../SelectedPlaceCardList';
import ContentTypeFilterItemList from '../ContentTypeFilterItemList';

interface Props {
  detailData: SchedulesTypeWithRegions;
  onClose: () => void;
}

const filterRegions = (regions: { regionIds: number[]; regionNames: string[] }) => {
  const { regionIds, regionNames } = regions;
  const result = regionIds.map((id, index) => ({
    id,
    name: regionNames[index],
  }));

  return result;
};

const PlanChangeModal = ({ onClose, detailData }: Props) => {
  const [keyword, setKeyword] = useState('');
  const [curRegionId, setCurRegionId] = useState(0);
  const [selectedPlaceList, setSelectedPlaceList] = useState<PlaceListTypes[]>([]);

  const title = useRecoilValue(planTitleAtom);
  const curDay = useRecoilValue(curDayAtom);
  const totalPlanDetail = useRecoilValue(totalPlanDetailAtom);

  const navigate = useNavigate();
  const regions = filterRegions(detailData?.regions);

  useEffect(() => {
    setSelectedPlaceList(totalPlanDetail[curDay - 1]?.items);
  }, [curDay, totalPlanDetail]);

  useEffect(() => {
    if (curRegionId === 0) setCurRegionId(detailData?.regions.regionIds[0]);
  }, [detailData]);

  const { mutate } = useMutation({
    mutationFn: (payload: SchedulesType) => usePatchTotalPlan(detailData.planId, payload),
    onSuccess: () => {
      alert('성공적으로 저장이 완료되었습니다!');
      navigate('/myPage/myPlan');
    },
    onError: () => {
      alert('저장이 실패했습니다!');
      onClose();
    },
  });

  const handlePlanChange = () => {
    const schedules = totalPlanDetail.map((data) => {
      return {
        scheduleId: data.day,
        date: data.date,
        schedulePlaces: data.items.map((schedulePlace, idx) => ({
          schedulePlaceId: schedulePlace.schedulePlaceId ? schedulePlace.schedulePlaceId : null,
          placeId: schedulePlace.placeId,
          memo: schedulePlace.memo ?? '',
          visitOrder: idx + 1,
          expense: schedulePlace.expense ?? 0,
        })),
      };
    });

    const result = {
      planId: detailData.planId,
      title,
      startDate: detailData.startDate,
      endDate: detailData.endDate,
      code: detailData.code,
      schedules,
    };

    mutate(result);
  };

  return (
    <ModalOverlay>
      <Wrapper>
        <TitleBox>
          <PlanTitle />
        </TitleBox>

        <Content>
          <SearchLayer>
            <section>
              {detailData && (
                <RegionCategoryList
                  data={regions}
                  type="BUTTON"
                  curRegionId={curRegionId}
                  setCurRegionId={setCurRegionId}
                />
              )}
              <EnterSearch placeHolder="장소를 검색해보세요!" setKeyword={setKeyword} />
              <ContentTypeFilterItemList />
            </section>

            {detailData && (
              <PlaceList keyword={keyword} setKeyword={setKeyword} curRegionId={curRegionId} />
            )}
          </SearchLayer>
          <DayLayer>
            <DayCategory />
            <SelectedPlaceCardList />
          </DayLayer>
          <MapLayer>
            <KaKaoMap list={selectedPlaceList} curDay={curDay} />
          </MapLayer>
        </Content>

        <ButtonBox>
          <CloseButton onClick={handlePlanChange} color={theme.colors.mainColor}>
            저장
          </CloseButton>
          <CloseButton onClick={onClose} color={theme.colors.darkGrey}>
            닫기
          </CloseButton>
        </ButtonBox>
      </Wrapper>
    </ModalOverlay>
  );
};

export default PlanChangeModal;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  position: fixed;

  width: 80vw;
  height: 95vh;

  background-color: white;
  overflow: hidden;
`;

const TitleBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 8%;
`;

const Content = styled.div`
  display: flex;

  width: 80vw;
  height: 83vh;

  border-top: 1px solid rgba(0, 0, 0, 0.1);
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
`;

const SearchLayer = styled.div`
  width: 25%;
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
`;

const ButtonBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  bottom: 0px;

  width: 100%;
  height: 7vh;
  background-color: white;
  z-index: 999;

  button {
    width: 75px;
    height: 45px;
    margin-top: 15px;
  }
`;
