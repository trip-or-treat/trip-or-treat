import { useEffect } from 'react';
import styled from 'styled-components';
import { useRecoilState, useRecoilValue } from 'recoil';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import modalStateAtom from 'src/atoms/modalStateAtom';
import regionIdAtom from 'src/atoms/regionIdAtom';
import RegionModal from '../RegionModal';
import RegionCard from '../common/RegionCard';
import { ReactComponent as Plus } from '../../assets/svgs/plus.svg';

const EmptyMyRegionList = () => {
  return (
    <EmptyBox>
      <p>
        아래의 <Plus />
        버튼으로 여행지를 추가해보세요!
      </p>
      <p>한 지역만 여행시 바로 다음을 눌러주세요.</p>
    </EmptyBox>
  );
};

const MyRegionList = () => {
  const myRegionList = useRecoilValue(myRegionListAtom);
  const [isModal, setModal] = useRecoilState(modalStateAtom);
  const currentId = useRecoilValue(regionIdAtom);

  const onClose = () => {
    setModal(false);
  };

  useEffect(() => {
    setModal(false);
  }, []);

  return (
    <Wrapper>
      <Title>
        <p>내 여행지</p>
        <p>(최대 2개 선택 가능)</p>
      </Title>
      <MyRegionLCardBox>
        <RegionCard item={myRegionList[0]} type="DEFAULT" />

        {[...myRegionList].slice(1).length < 1 ? (
          <EmptyMyRegionList />
        ) : (
          [...myRegionList]
            .slice(1)
            .map((item) => <RegionCard key={item.id} item={item} type="DELETE_BUTTON" />)
        )}
      </MyRegionLCardBox>
      {isModal && <RegionModal id={currentId} onClose={onClose} />}
    </Wrapper>
  );
};
export default MyRegionList;

const Wrapper = styled.div`
  width: 100%;
  height: 250px;

  padding: 20px 20px 0px 20px;
  margin-bottom: 5px;
  box-sizing: border-box;

  border-bottom: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};
`;

const Title = styled.div`
  p {
    &:first-child {
      margin-bottom: 5px;

      color: ${(props) => props.theme.colors.blackFont};
      font-size: 18px;
    }

    &:last-child {
      color: ${(props) => props.theme.colors.darkGrey};
      font-size: 12px;
    }
  }
`;

const EmptyBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  height: 100px;

  color: ${(props) => props.theme.colors.blackFont};
  font-size: 17px;

  p {
    font-family: 'Pretendard-Regular';
    line-height: 22px;
    margin: 3px 0px;

    &:last-child {
      color: ${(props) => props.theme.colors.darkGrey};
      font-size: 15px;
    }
  }

  svg {
    width: 15px;
    height: 15px;
    margin: 0px 3px;
  }
`;

const MyRegionLCardBox = styled.div`
  margin: 5px 0px;
`;
