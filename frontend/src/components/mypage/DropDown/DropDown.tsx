import styled from 'styled-components';
import { useEffect, useState } from 'react';

import media from 'src/styles/media';
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
  const [selected, setSelected] = useState(filterKeyArray[0]);

  useEffect(() => {
    setSelected(filterKeyArray[0]);
  }, []);

  return (
    <Wrapper>
      <ToggleBox
        onClick={() => {
          setOpen(!open);
        }}
      >
        <Selected>{selected}</Selected>
        <DropDownIcon />
      </ToggleBox>
      {open && (
        <DropMenu>
          {filterKeyArray.map((item, idx) => (
            <DropMenuItem
              key={item}
              onClick={() => {
                setSelected(item);
                setOpen(!open);
                setParams(filterValueArray[idx]);
              }}
            >
              {item}
            </DropMenuItem>
          ))}
        </DropMenu>
      )}
    </Wrapper>
  );
};

export default DropDown;

const Wrapper = styled.div`
  display: flex;
`;

const ToggleBox = styled.div`
  ${CommonContainer}

  width: 100%;
  height: 35px;

  border-radius: 35px;
  box-shadow: 0px 3px 5px 0px ${(props) => props.theme.colors.lightGrey};

  cursor: pointer;
  overflow: hidden;

  ${media.mobile`
    height: 55px;
  `}

  svg {
    ${media.mobile`
      width: 40px;
      height: 40px;
    `}
  }
`;

const DropMenu = styled.div`
  position: fixed;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  margin-top: 50px;
  width: max-content;
  padding: 10px 1%;

  border: solid 0.7px ${(props) => props.theme.colors.darkGrey};
  border-radius: 20px;
  background-color: white;

  font-family: 'Pretendard-Regular';
  font-size: 14px;

  z-index: 5;

  ${media.mobile`
    margin-top: 70px;
    padding: 15px 2%;
    
    font-size: 23px;
  `}
`;

const DropMenuItem = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 35px;

  border-radius: 20px;

  cursor: pointer;
`;

const Selected = styled.div`
  width: 70%;
  margin-right: 10px;

  ${media.mobile`
    margin-left: 10px;
    font-size: 23px;
  `}
`;
