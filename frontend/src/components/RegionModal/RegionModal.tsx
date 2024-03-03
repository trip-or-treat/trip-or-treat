import styled from 'styled-components';
import { useSetRecoilState } from 'recoil';

import overviewTitleAtom from 'src/atoms/overviewTitleAtom';
import { useRegionsMoreInformation } from 'src/hooks/api/useRegionsMoreInformation';
import { ReactComponent as Close } from 'src/assets/svgs/close.svg';
import RecommendPlaces from '../RecommendPlaces/RecommendPlaces';
import ModalOverlay from '../common/modal/ModalOverlay';
import ImageBox from '../common/modal/ImageBox';
import Overview from '../common/modal/Overview';
import CommonButton from '../common/CommonButton';

interface Props {
  id: number;
  onClose: () => void;
}

const RegionModal = ({ id, onClose }: Props) => {
  const { data: RegionsMoreInformationData } = useRegionsMoreInformation(id);
  const setTitle = useSetRecoilState(overviewTitleAtom);

  setTitle(false);

  return (
    <ModalOverlay>
      <StyledModalLayout>
        <StyledIcon>
          <Close onClick={onClose} />
        </StyledIcon>
        {RegionsMoreInformationData?.map((data) => (
          <>
            <StyledName>{data.name}</StyledName>
            <ImageBox imageOrigin={data.imageOrigin} />
            <Overview>{data.overview}</Overview>
            <RecommendPlaces key={data.id} recommendedPlaces={data.recommendedPlaces} />
            <StyledButtonInner>
              <CommonButton>일정만들기</CommonButton>
            </StyledButtonInner>
          </>
        ))}
      </StyledModalLayout>
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
