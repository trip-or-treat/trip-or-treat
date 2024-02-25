import { useRecoilState } from 'recoil';
import styled from 'styled-components';

import contentTypeId from 'src/atoms/contentTypeId';
import ContentTypeFilterItem from './ContentTypeFilterItem';

const ContentTypeFilterItemData = [
  {
    id: '12',
    name: '관광지',
  },
  {
    id: '14',
    name: '문화시설',
  },
  {
    id: '28',
    name: '레포츠',
  },
  {
    id: '32',
    name: '숙박',
  },
  {
    id: '38',
    name: '쇼핑',
  },
  {
    id: '39',
    name: '음식점',
  },
];

const ContentTypeFilterItemList = () => {
  const [prevContentTypeId, setPrevContentTypeId] = useRecoilState(contentTypeId);

  return (
    <Wrapper>
      {ContentTypeFilterItemData.map((data) => (
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
