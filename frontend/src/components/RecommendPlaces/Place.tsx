import styled from 'styled-components';

interface Props {
  name: string;
  overview: string;
  imageThumbnail: string;
}

const Place = ({ name, overview, imageThumbnail }: Props) => {
  return (
    <Wrapper>
      <StyledImageThumbnail imageThumbnail={imageThumbnail} />
      <StyledTextInner>
        <StyledName>{name}</StyledName>
        <StyledOverview>{overview.slice(0, overview.indexOf('.') + 1)}</StyledOverview>
      </StyledTextInner>
    </Wrapper>
  );
};

export default Place;

const Wrapper = styled.div`
  display: flex;

  margin: 30px;
`;

const StyledTextInner = styled.div`
  display: flex;
  flex-flow: column;

  margin-left: 15px;
`;

const StyledName = styled.h2`
  margin: 15px 0px 15px 0px;

  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-SemiBold';
`;

const StyledOverview = styled.p`
  display: -webkit-box;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: pre-wrap;
  word-break: break-all;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-Thin';
  line-height: 1.3;
`;

const StyledImageThumbnail = styled.div<{ imageThumbnail: string }>`
  flex: 0 0 auto;

  width: 111px;
  height: 108px;

  background-image: url(${(props) => props.imageThumbnail});
  background-size: cover;
  background-position: center;
  border-radius: 30px;
`;
