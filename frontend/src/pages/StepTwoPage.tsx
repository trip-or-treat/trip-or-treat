import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import EnterSearch from 'src/components/EnterSearch';
import MyRegionList from 'src/components/MyRegionList';
import SearchRegionList from 'src/components/SearchRegionList';
import KaKaoMap from 'src/components/KaKaoMap';

import curDayAtom from 'src/atoms/curDayAtom';
import myRegionListAtom from 'src/atoms/myRegionListAtom';

const StepTwoPage = () => {
  const [keyword, setKeyword] = useState('');
  const myRegionList = useRecoilValue(myRegionListAtom);
  const setCurDay = useSetRecoilState(curDayAtom);

  useEffect(() => {
    setCurDay(1);
  }, []);

  return (
    <Wrapper>
      <SearchLayer>
        <MyRegionList />

        <section>
          <EnterSearch placeHolder="여행지를 검색해보세요!" setKeyword={setKeyword} />
        </section>

        <SearchRegionList keyword={keyword} />
      </SearchLayer>

      <MapLayer>
        <KaKaoMap list={myRegionList} />
      </MapLayer>
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
  width: 25%;
  height: inherit;
  border-left: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};
  border-right: ${(props) => `1px solid ${props.theme.colors.lightGrey}`};

  box-sizing: border-box;

  section {
    padding: 0px 20px;
  }
`;

const MapLayer = styled.div`
  width: 75%;
  height: inherit;
`;
