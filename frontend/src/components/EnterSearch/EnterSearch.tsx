import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';

import { ReactComponent as FindIcon } from '../../assets/svgs/findIcon.svg';
import { ReactComponent as RotateIcon } from '../../assets/svgs/rotate.svg';

interface Props {
  placeHolder: string;
  setKeyword?: React.Dispatch<React.SetStateAction<string>>;
}

const EnterSearch = ({ placeHolder, setKeyword }: Props) => {
  const [value, setValue] = useState('');
  const { regionId } = useParams();
  const setContentTypeId = useSetRecoilState(contentTypeIdAtom);

  useEffect(() => {
    setValue('');
  }, [regionId]);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (setKeyword) setKeyword(value);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      if (value.trim() === '') {
        e.preventDefault();
        alert('검색어를 입력해주세요');
        setValue('');
        if (setKeyword) setKeyword('');
      }
    }
  };

  const handleRotate = () => {
    if (setKeyword) setKeyword('');
    setContentTypeId(null);
    setValue('');
  };

  const handleSearch = () => {
    if (value.trim() === '') {
      alert('검색어를 입력해주세요');
    }
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Button onClick={handleSearch}>
        <FindIcon />
      </Button>
      <Input
        placeholder={placeHolder}
        value={value}
        onChange={handleChange}
        onKeyDown={handleKeyDown}
      />
      <Button onClick={handleRotate}>
        <RotateIcon />
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
  padding-left: 15%;

  border: none;
  border-radius: 35px;
  outline: none;

  box-shadow: ${(props) => `0px 4px 4px 0px ${props.theme.colors.lightGrey}`};
  font-size: 14px;

  &::placeholder {
    color: ${(props) => props.theme.colors.darkGrey};
    font-family: 'Pretendard-Regular';
  }
`;

const Button = styled.button`
  position: absolute;
  top: 9px;
  padding: 5px;

  border: none;
  outline: none;

  background-color: inherit;
  cursor: pointer;

  svg {
    width: 20px;
    height: 15px;
    fill: ${(props) => props.theme.colors.darkGrey};
  }

  &:first-child {
    left: 10px;
  }

  &:last-child {
    right: 10px;
  }
`;
