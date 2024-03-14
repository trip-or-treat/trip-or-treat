import React, { useEffect, useRef } from 'react';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import { PlaceListTypes } from 'src/@types/api/placeList';
import useInfinityScroll from 'src/hooks/api/useInfinityScroll';
import { placeListFetcher } from 'src/api/placeList';
import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';
import modalStateAtom from 'src/atoms/modalStateAtom';

import PlaceCard from './PlaceCard';
import Loading from '../common/Loading';
import PlaceModal from '../PlaceModal';
import GhostImg from '../../assets/images/ghost.png';

interface Props {
  keyword: string;
  setKeyword: React.Dispatch<React.SetStateAction<string>>;
}

export const NotFoundResult = () => {
  return (
    <NotFoundResultBox>
      <p>찾으시는 정보가 없습니다.</p>
      <img src={GhostImg} alt="고스트 이미지" />
    </NotFoundResultBox>
  );
};

const PlaceList = ({ keyword, setKeyword }: Props) => {
  const observerRef = useRef(null);
  const { regionId } = useParams();
  const [prevContentTypeId, setContentTypeId] = useRecoilState(contentTypeIdAtom);
  const [isModal, setModal] = useRecoilState(modalStateAtom);

  const onClose = () => {
    setModal(false);
  };

  const { data, isLoading } = useInfinityScroll({
    observerRef,
    queryKey: 'placeList',
    regionId,
    fetcherFn: placeListFetcher,
    keyword,
    prevContentTypeId,
  });

  useEffect(() => {
    setKeyword('');
    setContentTypeId(null);
  }, [regionId]);

  return (
    <Wrapper>
      <Title>장소선택</Title>
      <PlaceListBox>
        {isLoading && <Loading type="SMALL" />}
        {data?.pages[0]?.data.length === 0 && <NotFoundResult />}
        {!isLoading && (
          <>
            {data?.pages?.map((items) =>
              items.data.map((placeCardItem: PlaceListTypes) => (
                <PlaceCard key={placeCardItem.id} placeCardItem={placeCardItem} type="ADD_BUTTON" />
              )),
            )}
            {data?.pages[data.pages.length - 1].data.length >= 10 && (
              <div ref={observerRef}>
                <Loading type="SMALL" />
              </div>
            )}
          </>
        )}
      </PlaceListBox>
      {isModal && <PlaceModal onClose={onClose} />}
    </Wrapper>
  );
};

export default PlaceList;

const Wrapper = styled.div`
  height: calc(100vh - 320px);
  overflow: auto;
`;

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
`;

const NotFoundResultBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 25px 0px;
  img {
    margin-left: 10px;
    width: 50px;
    height: 50px;
  }
`;
