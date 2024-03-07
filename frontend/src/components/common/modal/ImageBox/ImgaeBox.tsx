import styled from 'styled-components';

interface Props {
  imageThumbnail: string;
}

const ImageBox = ({ imageThumbnail }: Props) => {
  return <StyledImageBox src={imageThumbnail} />;
};

export default ImageBox;

const StyledImageBox = styled.img`
  width: 457px;
  height: 217px;
  margin-bottom: 40px;

  background-size: cover;
  background-position: center;

  border-radius: 30px;
  box-shadow: 1px 1px 1px 0px ${(props) => props.theme.colors.darkGrey};
`;
