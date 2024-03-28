import styled from 'styled-components';

import { Regions } from 'src/@types/api/regions';
import RegionCategoryCard from './RegionCategoryCard';

interface Props {
  data: Regions[];
}

const RegionCategoryList = ({ data }: Props) => {
  return (
    <Wrapper>
      {data.map((item) => (
        <RegionCategoryCard key={item.id} item={item} />
      ))}
    </Wrapper>
  );
};

export default RegionCategoryList;

const Wrapper = styled.ul`
  display: flex;
`;
