import styled from 'styled-components';

import { useRegions } from 'src/hooks/api/useRegions';
import Loading from 'src/components/common/Loading';
import RegionItem from './RegionItem';

const RegionList = () => {
  const { data: regionsData, isLoading, isError } = useRegions();

  return (
    <Wrapper>
      {isLoading && <Loading type="MEDIUM" />}
      {isError && <CannotLoading>데이터를 불러오는 데 실패했습니다.</CannotLoading>}
      <ListContainer>
        {!isLoading &&
          regionsData?.map((data) => (
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
