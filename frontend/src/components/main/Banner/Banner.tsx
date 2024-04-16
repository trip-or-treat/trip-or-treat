import styled from 'styled-components';

import media from 'src/styles/media';
import mainBannerBgImg from '../../../assets/images/mainBanner.png';
import StartButton from './StartButton';

const Banner = () => {
  return (
    <Container>
      <BannerImg src={mainBannerBgImg} />
      <StartButton />
    </Container>
  );
};

export default Banner;

const Container = styled.div`
  width: 100%;
  margin: 150px 0px 50px 0px;

  text-align: center;
`;

const BannerImg = styled.img<{ src: string }>`
  width: 65%;
  margin: 0px 0px 50px 0px;

  background-image: url(${(props) => props.src});
  background-size: cover;
  background-repeat: no-repeat;

  ${media.mobile`
    width: 83%;
    margin: 0px 0px 40px 20px;
  `}
`;
