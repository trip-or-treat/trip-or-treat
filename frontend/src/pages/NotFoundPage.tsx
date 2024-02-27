import styled from 'styled-components';

import notFound from '../assets/images/notFound.png';

const NotFoundPage = () => {
  return <NotFound src={notFound} />;
};

export default NotFoundPage;

const NotFound = styled.img<{ src: string }>`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);

  background-image: url{$(props) => props.src};
`;
