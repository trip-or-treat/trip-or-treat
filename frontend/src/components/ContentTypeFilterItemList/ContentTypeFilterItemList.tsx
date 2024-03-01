import { useRecoilState } from 'recoil';
import styled from 'styled-components';

import { useContentType } from 'src/hooks/api/useContentType';
import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';
import ContentTypeFilterItem from './ContentTypeFilterItem';

const ContentTypeFilterItemList = () => {
  const [prevContentTypeId, setPrevContentTypeId] = useRecoilState(contentTypeIdAtom);
  const { data: contentTypeData, isLoading } = useContentType();

  return (
    <Wrapper>
      {isLoading && <div>로딩중...</div>}

      {!isLoading &&
        contentTypeData?.map((data) => (
          <ContentTypeFilterItem
            key={data.id}
            id={data.id}
            title={data.name}
            prevContentTypeId={prevContentTypeId}
            setPrevContentTypeId={setPrevContentTypeId}
          />
        ))}
    </Wrapper>
  );
};

export default ContentTypeFilterItemList;

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;

  margin: 15px 0px;
`;
