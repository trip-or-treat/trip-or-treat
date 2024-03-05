import { useRef } from 'react';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { PlaceListTypes } from 'src/@types/api/placeList';
import useInfinityScroll from 'src/hooks/api/useInfinityScroll';
import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';
import PlaceCard from './PlaceCard';
import Loading from '../common/Loading';

interface Props {
  keyword: string;
}

const PlaceList = ({ keyword }: Props) => {
  const observerRef = useRef(null);
  const { regionId } = useParams();
  const prevContentTypeId = useRecoilValue(contentTypeIdAtom);

  const { data, isLoading } = useInfinityScroll({
    observerRef,
    regionId,
    keyword,
    prevContentTypeId,
  });

  return (
    <>
      <Title>장소선택</Title>
      <PlaceListBox>
        {isLoading && <Loading type="SMALL" />}

        {!isLoading && (
          <>
            {data?.pages?.map((items) =>
              items.map((placeCardItem: PlaceListTypes) => (
                <PlaceCard key={placeCardItem.id} placeCardItem={placeCardItem} type="ADD_BUTTON" />
              )),
            )}
            <div ref={observerRef}>
              <Loading type="SMALL" />
            </div>
          </>
        )}
      </PlaceListBox>
    </>
  );
};

export default PlaceList;

const Title = styled.div`
  text-align: center;
  padding-bottom: 10px;
  border-bottom: ${(props) => `0.1px solid ${props.theme.colors.darkGrey}`};

  font-size: 16px;
  font-family: 'Pretendard-SemiBold';
  color: #6f6d6d;
`;

const PlaceListBox = styled.div`
  padding: 0px 20px;
  margin-top: 25px;
  height: 350px;
  overflow: auto;
`;
