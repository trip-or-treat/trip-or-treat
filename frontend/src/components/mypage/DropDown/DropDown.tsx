import styled from 'styled-components';
import { useEffect, useState } from 'react';

import { ReactComponent as DropDownIcon } from '../../../assets/svgs/dropDown.svg';
import { CommonContainer } from '../../../styles/CommonContainer';

interface Props {
  filter: object;
  setParams(arg: string): void;
}

const DropDown = ({ filter, setParams }: Props) => {
  const filterKeyArray: string[] = Object.keys(filter);
  const filterValueArray: string[] = Object.values(filter);

  const [open, setOpen] = useState(false);
  const [clicked, setClicked] = useState(filterKeyArray[0]);

  useEffect(() => {
    setClicked(filterKeyArray[0]);
  }, []);

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
          {filterKeyArray.map((item, idx) => (
            <DropMenuItem
              key={item}
              onClick={() => {
                setClicked(item);
                setOpen(!open);
                setParams(filterValueArray[idx]);
              }}
            >
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
  box-shadow: 0px 3px 5px 0px ${(props) => props.theme.colors.lightGrey};

  cursor: pointer;
  overflow: hidden;
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
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 35px;
  border-radius: 20px;
  /* background-color: yellow; */
  cursor: pointer;
`;

const Selected = styled.div`
  /* width: 35px; */
`;
