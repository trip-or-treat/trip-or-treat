import styled from 'styled-components';

import { useRegions } from 'src/hooks/api/useRegions';
import RegionItem from './RegionItem';

const RegionList = () => {
  const { data: regionsData, isLoading } = useRegions();

  return (
    <Wrapper>
      {isLoading && <Loading />}
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

const Loading = styled.div`
  width: 43px;
  height: 43px;
  margin: 10px auto;

  border: 7px solid #dcdcdc;
  border-bottom: 7px solid ${(props) => props.theme.colors.mainColor};
  border-radius: 50%;

  animation: load 1.5s linear infinite;

  @keyframes load {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }
`;
