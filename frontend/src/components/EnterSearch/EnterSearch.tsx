import React, { useState } from 'react';
import styled from 'styled-components';

import { ReactComponent as FindIcon } from '../../assets/svgs/findIcon.svg';

interface Props {
  placeHolder: string;
  setKeyword?: React.Dispatch<React.SetStateAction<string>>;
}

const EnterSearch = ({ placeHolder, setKeyword }: Props) => {
  const [value, setValue] = useState('');

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (value.trim() === '') {
      alert('검색어를 입력해주세요');
      if (setKeyword) setKeyword(value);
      setValue('');
      return;
    }

    if (setKeyword) setKeyword(value);
    setValue('');
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Input placeholder={placeHolder} value={value} onChange={handleChange} />
      <Button>
        <FindIcon />
      </Button>
    </Form>
  );
};

export default EnterSearch;

const Form = styled.form`
  display: flex;
  justify-content: center;
  position: relative;

  margin: 20px 0px;
`;

const Input = styled.input`
  width: 90%;

  padding: 15px;
  padding-left: 20px;

  border: none;
  border-radius: 35px;
  outline: none;

  box-shadow: ${(props) => `0px 4px 4px 0px ${props.theme.colors.lightGrey}`};
  font-size: 13px;

  &::placeholder {
    color: ${(props) => props.theme.colors.darkGrey};
  }
`;

const Button = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;

  padding: 5px;

  border: none;
  outline: none;

  background-color: inherit;
  cursor: pointer;

  svg {
    width: 20px;
    height: 15px;
  }
`;
