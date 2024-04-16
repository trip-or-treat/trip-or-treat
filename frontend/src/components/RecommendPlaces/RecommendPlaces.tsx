import styled from 'styled-components';
import media from 'src/styles/media';

import ghostImage from 'src/assets/images/ghost.png';
import Place from './Place';

interface Props {
  recommendedPlaces: {
    id: number;
    name: string;
    overview: string;
    imageThumbnail: string;
  }[];
}
const RecommendPlaces = ({ recommendedPlaces }: Props) => {
  return (
    <StyledModalLayout>
      <StyledPlaces>추천 여행지</StyledPlaces>
      <StyledGhostImage />
      {recommendedPlaces.map((data) => (
        <Place
          key={data.id}
          name={data.name}
          overview={data.overview}
          imageThumbnail={data.imageThumbnail}
        />
      ))}
    </StyledModalLayout>
  );
};

export default RecommendPlaces;

const StyledModalLayout = styled.div`
  position: relative;
  width: 482px;
  height: 455px;
  text-align: left;
  border-left: 1px solid ${(props) => props.theme.colors.lightGrey};
  margin-left: 30px;

  ${media.mobile`
    border-left: none;
    border-top: 1px solid #D9D9D9;
    margin-left: -30px;
  `}
`;

const StyledPlaces = styled.div`
  margin: 30px 0px 0px 30px;
  text-align: left;
  text-decoration: underline;
  text-underline-position: under;
  color: ${(props) => props.theme.colors.mainColor};
  font-family: 'Pretendard-Bold';
  font-size: 20px;

  ${media.mobile`
    text-align: center; 
  `}
`;

const StyledGhostImage = styled.div`
  position: absolute;

  width: 80px;
  height: 80px;
  top: 0;
  right: 0;

  background-size: cover;
  background-image: url(${ghostImage});

  ${media.mobile`
    display: none;
  `}
`;
