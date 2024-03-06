import styled from 'styled-components';
import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';

import Loading from 'src/components/common/Loading';
import { useRegions } from 'src/hooks/api/useRegions';
import { Regions } from 'src/@types/api/regions';
import regionsAtom from 'src/atoms/regionsAtom';

import RegionItem from './RegionItem';

interface RegionListData {
  data: { data: Regions[] };
  isLoading: boolean;
  isError: boolean;
}

const RegionList = () => {
  const { data: regionsApi, isLoading, isError }: RegionListData = useRegions();
  const setRegions = useSetRecoilState(regionsAtom);

  useEffect(() => {
    if (regionsApi?.data) setRegions(regionsApi.data);
  }, [regionsApi]);

  return (
    <Wrapper>
      {isLoading && <Loading type="MEDIUM" />}
      {isError && <CannotLoading>데이터를 불러오는 데 실패했습니다.</CannotLoading>}
      <ListContainer>
        {!isLoading &&
          regionsApi?.data.map((data) => (
            <RegionItem key={data.id} src={data.imageThumbnail} name={data.name} />
          ))}
      </ListContainer>
    </Wrapper>
  );
};

export default RegionList;

const Wrapper = styled.div``;

const ListContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(4, ${(props) => props.theme.size.regionItemSize});
  column-gap: 55px;
  row-gap: 155px;

  justify-content: center;
`;

const CannotLoading = styled.div`
  height: 70px;
  margin-top: 50px;

  font-family: 'Pretendard-SemiBold';
  font-size: 20px;
  color: ${(props) => props.theme.colors.blackFont};
  text-align: center;
`;
