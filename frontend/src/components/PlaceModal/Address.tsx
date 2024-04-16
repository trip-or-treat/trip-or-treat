import media from 'src/styles/media';
import styled from 'styled-components';

interface Props {
  address: string;
}

const Address = ({ address }: Props) => {
  return (
    <Wrapper>
      <StyledTitle>주소</StyledTitle>
      <StyledText>{address}</StyledText>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  ${media.mobile`
    display: flex;
    justify-content: space-between;
    position: relative;
    width: 65%;
`}
`;

const StyledTitle = styled.h3`
  margin-bottom: 15px;

  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-Bold';
`;
const StyledText = styled.p`
  margin-bottom: 15px;

  font-size: 17px;
  line-height: 1.5;
  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-Regular';
`;

export default Address;
