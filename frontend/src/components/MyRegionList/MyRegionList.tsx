import styled from 'styled-components';
import { useRecoilValue } from 'recoil';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import RegionCard from '../common/RegionCard';

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
          <div>데이터가 없습니다.</div>
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

const MyRegionLCardBox = styled.div`
  margin: 5px 0px;
`;
