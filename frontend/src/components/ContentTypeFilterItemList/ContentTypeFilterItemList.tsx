import styled from 'styled-components';

import { useContentType } from 'src/hooks/api/useContentType';
import ContentTypeFilterItem from './ContentTypeFilterItem';

const ContentTypeFilterItemList = () => {
  const { data: contentTypeData, isLoading } = useContentType();

  return (
    <Wrapper>
      {isLoading && <Loading />}

      {!isLoading &&
        contentTypeData?.map((data) => (
          <ContentTypeFilterItem key={data.id} id={data.id} title={data.name} />
        ))}
    </Wrapper>
  );
};

export default ContentTypeFilterItemList;

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;

  margin: 5px 0px;
`;

const Loading = styled.div`
  width: 15px;
  height: 15px;
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
