import styled from 'styled-components';

import { useRecoilValue } from 'recoil';
import regionsAtom from 'src/atoms/regionsAtom';
import RegionCard from '../common/RegionCard';

const SearchRegionList = () => {
  const regions = useRecoilValue(regionsAtom);
  return (
    <Wrapper>
      {regions.map((item) => (
        <RegionCard key={item.id} item={item} type="ADD_BUTTON" />
      ))}
    </Wrapper>
  );
};

export default SearchRegionList;

const Wrapper = styled.div`
  overflow-y: auto;

  width: 100%;
  height: 288px;
  padding: 0px 20px;
  margin-bottom: 5px;

  box-sizing: border-box;
`;
