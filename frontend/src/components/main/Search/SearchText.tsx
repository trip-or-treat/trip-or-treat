import styled from 'styled-components';

const SearchText = () => {
  return <Text>어디로 여행을 떠나시나요?</Text>;
};

export default SearchText;

const Text = styled.div`
  text-align: center;
  font-family: 'Pretendard-Bold';
  font-size: 30px;
  color: ${(props) => props.theme.colors.blackFont};
`;
