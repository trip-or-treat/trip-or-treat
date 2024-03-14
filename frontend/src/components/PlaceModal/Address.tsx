import styled from 'styled-components';

interface Props {
  address: string;
}

const Address = ({ address }: Props) => {
  return (
    <>
      <StyledTitle>주소</StyledTitle>
      <StyledText>{address}</StyledText>
    </>
  );
};

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
