import { decode } from 'html-entities';
import styled from 'styled-components';

import defaultImg from 'src/assets/images/defaultImg.png';

interface Props {
  name: string;
  overview: string;
  imageThumbnail: string;
}

const Place = ({ name, overview, imageThumbnail }: Props) => {
  const decodedText = decode(overview)
    .replace(/<br\s*\/?>/g, '')
    .replace(/\*/g, '');

  return (
    <Wrapper>
      <StyledImageThumbnail src={imageThumbnail.length === 0 ? defaultImg : imageThumbnail} />
      <StyledTextInner>
        <StyledName>{name}</StyledName>
        <StyledOverview>{decodedText}</StyledOverview>
      </StyledTextInner>
    </Wrapper>
  );
};

export default Place;

const Wrapper = styled.div`
  display: flex;

  margin: 25px 20px 0px 30px;
`;

const StyledTextInner = styled.div`
  display: flex;
  flex-flow: column;

  margin-left: 15px;

  font-size: 17px;
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

const StyledImageThumbnail = styled.img`
  flex: 0 0 auto;

  width: 111px;
  height: 108px;

  background-size: cover;
  background-position: center;
  border-radius: 30px;
`;
