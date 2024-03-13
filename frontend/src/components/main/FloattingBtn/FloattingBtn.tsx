import styled from 'styled-components';
import { ReactComponent as UpScroll } from '../../../assets/svgs/upScroll.svg';

const FloattingBtn = () => {
  const MoveToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };
  return (
    <StyledBtn onClick={MoveToTop}>
      <UpScroll />
    </StyledBtn>
  );
};

export default FloattingBtn;

const StyledBtn = styled.button`
  position: fixed;

  width: 60px;
  height: 60px;
  margin: 30% 0 0 88%;

  background-color: white;
  border: none;
  border-radius: 50%;
  box-shadow: 0px 3px 4px 0px darkGray;

  cursor: pointer;

  svg {
    width: 45px;
    height: 45px;
  }
`;
