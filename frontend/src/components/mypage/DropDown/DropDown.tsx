import styled from 'styled-components';
import { useState } from 'react';

import { ReactComponent as DropDownIcon } from '../../../assets/svgs/dropDown.svg';
import { CommonContainer } from '../../../styles/CommonContainer';

interface Props {
  filter: string;
}

const DropDown = ({ filter }: Props) => {
  const filterArray = filter.split(',');

  const [open, setOpen] = useState(false);
  const [clicked, setClicked] = useState(filterArray[0]);

  // console.log(filter);
  // console.log(filterArray);

  // console.log(open);
  // console.log(clicked);

  return (
    <div>
      <ToggleBox
        onClick={() => {
          setOpen(!open);
        }}
      >
        <Selected>{clicked}</Selected>
        <DropDownIcon />
      </ToggleBox>
      {open && (
        <DropMenu>
          {filterArray.map((item) => (
            <DropMenuItem key={item} onClick={() => setClicked(item)}>
              {item}
            </DropMenuItem>
          ))}
        </DropMenu>
      )}
    </div>
  );
};

export default DropDown;

const ToggleBox = styled.div`
  ${CommonContainer}

  height: 35px;

  border-radius: 35px;
  box-shadow: 0px 4px 4px 0px ${(props) => props.theme.colors.lightGrey};

  cursor: pointer;
`;

const DropMenu = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  border: solid 0.7px ${(props) => props.theme.colors.darkGrey};
  border-radius: 20px;
  background-color: white;

  font-family: 'Pretendard-Regular';
  font-size: 14px;
`;

const DropMenuItem = styled.div`
  height: 35px;
`;

const Selected = styled.div`
  /* width: 35px; */

  background-color: blue;
`;
