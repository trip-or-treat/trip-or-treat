import styled from 'styled-components';
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
  margin: 80px 0px 100px 0px;

  text-align: center;
`;

const BannerImg = styled.img<{ src: string }>`
  margin: 0px 0px 90px 0px;

  background-image: url(${(props) => props.src});
  background-repeat: cover;
`;
