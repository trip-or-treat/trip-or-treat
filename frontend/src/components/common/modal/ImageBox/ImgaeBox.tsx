import styled from 'styled-components';

interface Props {
  imageOrigin: string;
}

const ImageBox = ({ imageOrigin }: Props) => {
  return <StyledImageBox imageOrigin={imageOrigin} />;
};

export default ImageBox;

const StyledImageBox = styled.div<{ imageOrigin: string }>`
  width: 457px;
  height: 217px;

  border-radius: 30px;
  box-shadow: 1px 1px 1px 0px darkgray;

  background-image: url(${(props) => props.imageOrigin});
  background-size: cover;
  background-position: center;
`;
