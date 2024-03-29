import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { useSetRecoilState, useRecoilValue } from 'recoil';

import { RegionsMoreInformation } from 'src/@types/api/regionsMoreInformation';
import { useRegionsMoreInformation } from 'src/hooks/api/useRegionsMoreInformation';
import { ReactComponent as Close } from 'src/assets/svgs/close.svg';
import createScheduleAtom from 'src/atoms/createScheduleAtom';
import overviewTitleAtom from 'src/atoms/overviewTitleAtom';

import Loading from '../common/Loading';
import ModalOverlay from '../common/modal/ModalOverlay';
import ImageBox from '../common/modal/ImageBox';
import Overview from '../common/modal/Overview';
import CommonButton from '../common/CommonButton';
import RecommendPlaces from '../RecommendPlaces/RecommendPlaces';

interface Props {
  id: number;
  onClose: () => void;
}

interface MoreInformation {
  data: {
    data: RegionsMoreInformation;
  };
  isLoading: boolean;
  isError: boolean;
}

const RegionModal = ({ id, onClose }: Props) => {
  const {
    data: RegionsMoreInformationApi,
    isLoading,
    isError,
  }: MoreInformation = useRegionsMoreInformation(id);
  const setTitle = useSetRecoilState(overviewTitleAtom);
  const iscreateSchedule = useRecoilValue(createScheduleAtom);

  useEffect(() => {
    setTitle(false);
  }, []);

  if (isError) {
    onClose();
  }

  return (
    <ModalOverlay>
      {isLoading ? (
        <Loading type="LARGE" />
      ) : (
        <StyledModalLayout key={RegionsMoreInformationApi.data.id}>
          <StyledIcon>
            <Close onClick={onClose} />
          </StyledIcon>
          <StyledName>{RegionsMoreInformationApi.data.name}</StyledName>
          <StyledImageBoxInner>
            <ImageBox image={RegionsMoreInformationApi.data.imageThumbnail} />
          </StyledImageBoxInner>
          <StyledOverviewInner>
            <Overview overview={RegionsMoreInformationApi.data.overview} />
          </StyledOverviewInner>
          <RecommendPlaces
            key={RegionsMoreInformationApi.data.id}
            recommendedPlaces={RegionsMoreInformationApi.data.recommendedPlaces}
          />
          <StyledButtonInner>
            {iscreateSchedule ? (
              <Link to={`date/${RegionsMoreInformationApi.data.id}`}>
                <CommonButton>일정만들기</CommonButton>
              </Link>
            ) : (
              <StyledFooter />
            )}
          </StyledButtonInner>
        </StyledModalLayout>
      )}
    </ModalOverlay>
  );
};

export default RegionModal;

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
  margin: 55px 0px 35px 25px;

  font-family: 'Pretendard-Bold';
  color: ${(props) => props.theme.colors.blackFont};
  font-size: 35px;
  text-align: left;
`;

const StyledButtonInner = styled.div`
  position: relative;
  top: 35px;
  right: 105px;
`;

const StyledIcon = styled.div`
  position: relative;
  top: 17px;
  left: 962px;

  width: 21px;
  height: 21px;

  cursor: pointer;
`;

const StyledImageBoxInner = styled.div`
  margin-bottom: 40px;
`;

const StyledOverviewInner = styled.div`
  margin-bottom: 110px;
`;

const StyledFooter = styled.div`
  width: 280px;
  height: 60px;
`;
