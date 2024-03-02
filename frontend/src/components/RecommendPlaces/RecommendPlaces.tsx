import styled from 'styled-components';

import ghostImage from 'src/assets/images/ghost.png';

const RecommendPlace = () => {
  return (
    <StyledModalLayout>
      <StyledPlaces>추천 여행지</StyledPlaces>
      <StyledGhostImage />
    </StyledModalLayout>
  );
};

export default RecommendPlace;

const StyledModalLayout = styled.div`
  position: relative;

  width: 482px;
  height: 494px;
`;

const StyledPlaces = styled.div`
  margin-left: 30px;

  text-decoration: underline;
  text-underline-position: under;
  color: ${(props) => props.theme.colors.mainColor};
  font-family: 'Pretendard-Bold';
  font-size: 20px;
`;

const StyledGhostImage = styled.div`
  position: absolute;

  width: 80px;
  height: 80px;
  top: 0;
  right: 0;

  background-size: cover;
  background-image: url(${ghostImage});
`;
