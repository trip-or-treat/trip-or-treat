import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import styled from 'styled-components';

import contentTypeIdAtom from 'src/atoms/contentTypeIdAtom';
import { ReactComponent as FindIcon } from '../../assets/svgs/findIcon.svg';

interface Props {
  placeHolder: string;
}

const EnterSearch = ({ placeHolder }: Props) => {
  const { regionId } = useParams();
  const [keyword, setKeyword] = useState('');
  const prevContentTypeId = useRecoilValue(contentTypeIdAtom);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (keyword.trim() === '') {
      alert('검색어를 입력해주세요');
      setKeyword('');
      return;
    }

    // TODO : API 요청 기능 구현
    // keyword : 검색어
    // rgionId : url에 존재하는 지역 id
    // prevContentTypeId : content_type_id인데 필터링이 없으면 null,  필터링 되면 string형식의 문자열 (eg. '12')
    console.log('keyword', keyword, 'regionId', regionId, 'prevContentTypeId', prevContentTypeId);
    setKeyword('');
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setKeyword(e.target.value);
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Input placeholder={placeHolder} value={keyword} onChange={handleChange} />
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
