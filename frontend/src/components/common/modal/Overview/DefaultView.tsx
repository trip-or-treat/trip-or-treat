import styled from 'styled-components';

import ghostImage from 'src/assets/images/ghost.png';

const DefaultView = () => {
  return (
    <StyledDefaultView>
      정보를 불러올 수 없어요. TOT
      <StyledGhostImage />
    </StyledDefaultView>
  );
};

export default DefaultView;

const StyledDefaultView = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const StyledGhostImage = styled.div`
  width: 50px;
  height: 50px;
  margin-left: 5px;

  background-size: cover;
  background-image: url(${ghostImage});
`;
