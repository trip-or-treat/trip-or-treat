import styled from 'styled-components';
import { useRecoilValue } from 'recoil';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
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

  if (myRegionList.length === 0) return <div />;

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
      font-family: 'Pretendard-Regular';
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
