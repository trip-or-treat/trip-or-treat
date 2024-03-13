import { styled } from 'styled-components';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { useEffect } from 'react';

import { usePlaceInfo } from 'src/hooks/api/usePlaceInfo';
import { PlaceInfo } from 'src/@types/api/placeInfo';
import { ReactComponent as Close } from 'src/assets/svgs/close.svg';
import placeIdAtom from 'src/atoms/placeIdAtom';
import overviewTitleAtom from 'src/atoms/overviewTitleAtom';
import contentTypelistAtom from 'src/atoms/contentTypeListAtom';

import ModalOverlay from '../common/modal/ModalOverlay';
import ImageBox from '../common/modal/ImageBox';
import Overview from '../common/modal/Overview';
import Address from './Address';

interface Props {
  onClose: () => void;
}

interface Info {
  data: {
    data: PlaceInfo;
  };
  isLoading: boolean;
  isError: boolean;
}

const PlaceModal = ({ onClose }: Props) => {
  const { id: currentId, name: content } = useRecoilValue(placeIdAtom);
  const { data: placeInfoApi, isLoading, isError }: Info = usePlaceInfo(currentId);
  const setTitle = useSetRecoilState(overviewTitleAtom);
  const contentTypeList = useRecoilValue(contentTypelistAtom);

  const findContentName = (): string | undefined => {
    const target = contentTypeList.find((type) => type.id === placeInfoApi.data.contentTypeId);
    return target?.name;
  };

  if (isError) {
    onClose();
  }

  useEffect(() => {
    setTitle(true);
  }, []);

  return (
    <ModalOverlay>
      {!isLoading && (
        <StyledModalLayout>
          <StyledIcon>
            <Close onClick={onClose} />
          </StyledIcon>
          <StyledName>
            {placeInfoApi.data.name}
            <StyledContent>{content}</StyledContent>
          </StyledName>
          <StyledContentType>{findContentName()}</StyledContentType>
          <StyledImageBoxInner>
            <ImageBox imageThumbnail={placeInfoApi.data.imageThumbnail} />
          </StyledImageBoxInner>
          <Address address={placeInfoApi.data.address} />
          <Overview overview={placeInfoApi.data.overview} />
          <StyledReviewIneer>리뷰 업데이트 예정이에요! 🙇🏻</StyledReviewIneer>
        </StyledModalLayout>
      )}
    </ModalOverlay>
  );
};

export default PlaceModal;

const StyledModalLayout = styled.div`
  display: flex;
  flex-flow: column;
  align-content: center;
  justify-content: center;
  flex-wrap: wrap;

  width: 1064px;
  height: 684px;
  border: none;
  border-radius: 20px;
  background-color: ${(props) => props.theme.colors.whiteFont};
`;

const StyledName = styled.div`
  width: 430px;
  margin-bottom: 15px;

  font-family: 'Pretendard-Bold';
  color: ${(props) => props.theme.colors.blackFont};
  font-size: 25px;
  text-align: left;
`;

const StyledContent = styled.span`
  margin-left: 10px;

  font-family: 'Pretendard-Thin';
  color: ${(props) => props.theme.colors.blackFont};
  font-size: 17px;
`;

const StyledContentType = styled.div`
  text-align: center;

  width: 60px;
  padding: 10px;
  margin-bottom: 20px;

  border-radius: 25px;
  box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.2);
  background-color: #ffffff;
  color: #3a3939;
  font-size: 12px;
  font-family: 'Pretendard-Regular';
`;

const StyledImageBoxInner = styled.div`
  margin-bottom: 30px;
`;

const StyledReviewIneer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 489px;
  height: 580px;

  background-color: ${(props) => props.theme.colors.whiteFont};
`;

const StyledIcon = styled.div`
  position: relative;
  bottom: 17px;
  left: 950px;

  width: 21px;
  height: 21px;

  cursor: pointer;
`;
