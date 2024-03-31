import styled from 'styled-components';
import React from 'react';

import { Regions } from 'src/@types/api/regions';
import RegionCategoryCard from './RegionCategoryCard';

interface Props {
  type: 'BUTTON' | 'LINK';
  data:
    | Regions[]
    | {
        id: number;
        name: string;
      }[];
  curRegionId?: number;
  setCurRegionId?: React.Dispatch<React.SetStateAction<number>>;
}

const RegionCategoryList = ({ type = 'LINK', data, curRegionId, setCurRegionId }: Props) => {
  return (
    <Wrapper>
      {data?.map((item) => (
        <RegionCategoryCard
          key={item.id}
          item={item}
          type={type}
          curRegionId={curRegionId}
          setCurRegionId={setCurRegionId}
        />
      ))}
    </Wrapper>
  );
};

export default RegionCategoryList;

const Wrapper = styled.ul`
  display: flex;
`;
