import styled from 'styled-components';

import { useContentType } from 'src/hooks/api/useContentType';
import ContentTypeFilterItem from './ContentTypeFilterItem';
import Loading from '../common/Loading';

const ContentTypeFilterItemList = () => {
  const { data: contentTypeData, isLoading } = useContentType();

  return (
    <Wrapper>
      {isLoading && <Loading type="SMALL" />}

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
