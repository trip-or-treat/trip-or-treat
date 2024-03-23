import styled from 'styled-components';

import { CommonContainer } from '../CommonContainer';
import { ReactComponent as DropDownIcon } from '../../../assets/svgs/dropDown.svg';

const SearchToggle = () => {
  return (
    <ToggleBox>
      <Filter>다가오는 여행</Filter>
      <DropDownIcon />
    </ToggleBox>
  );
};

export default SearchToggle;

const ToggleBox = styled.div`
  ${CommonContainer}

  width: 25%;
  height: 48px;

  border-radius: 35px;
  box-shadow: 0px 4px 4px 0px ${(props) => props.theme.colors.lightGrey};
`;

const Filter = styled.div``;
