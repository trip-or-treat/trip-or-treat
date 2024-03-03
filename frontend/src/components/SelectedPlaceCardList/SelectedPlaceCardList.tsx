import { useRecoilValue } from 'recoil';
import styled from 'styled-components';

import totalPlanAtom from 'src/atoms/totalPlanAtom';
import curDayAtom from 'src/atoms/curDayAtom';
import PlaceCard from '../PlaceList/PlaceCard';
import GhostImg from '../../assets/images/ghost.png';

const EmptyPlaceItem = () => {
  return (
    <EmptyPlaceItemWrapper>
      <p>날짜별로 장소를 1개 이상 선택해주세요!</p>
      <img src={GhostImg} alt="고스트 이미지" />
    </EmptyPlaceItemWrapper>
  );
};

const SelectedPlaceCardList = () => {
  const curDay = useRecoilValue(curDayAtom);
  const totalPlan = useRecoilValue(totalPlanAtom);
  const filteredCurDay = totalPlan.filter((item) => item.day === curDay);

  return (
    <Wrapper>
      {filteredCurDay[0].items.length === 0 && <EmptyPlaceItem />}
      {filteredCurDay.map((itemArr) => (
        <div key={itemArr.date}>
          {itemArr.items.map((item) => (
            <PlaceCard key={item.id} placeCardItem={item} type="DRAG_AND_DROP" />
          ))}
        </div>
      ))}
    </Wrapper>
  );
};

export default SelectedPlaceCardList;

const Wrapper = styled.div`
  margin-top: 20px;
`;

const EmptyPlaceItemWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
  font-size: 17px;
  color: ${(props) => props.theme.colors.blackFont};

  img {
    margin-top: 20px;
    width: 80px;
    height: 80px;
  }
`;
