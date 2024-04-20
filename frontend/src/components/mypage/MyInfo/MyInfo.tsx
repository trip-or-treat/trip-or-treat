import styled from 'styled-components';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { useMutation } from '@tanstack/react-query';

import media from 'src/styles/media';
import { useModifyNickname } from 'src/hooks/api/useModifyNickname';
import { ReactComponent as EditIcon } from '../../../assets/svgs/edit.svg';

interface Props {
  email: string;
  nickname: string;
}

const MyInfo = ({ email, nickname }: Props) => {
  const profileRef = useRef<HTMLInputElement>(null);
  const [isChangingNickname, setIsChangingNickname] = useState(false);
  const [curNickname, setCurNickname] = useState('');

  const { mutate } = useMutation({
    mutationFn: (prop: { nickname: string }) => useModifyNickname(prop),
    onSuccess: () => {
      alert('성공적으로 닉네임이 변경되었습니다!');
    },

    onError: () => {
      alert('닉네임 변경이 실패했습니다!');
    },
  });

  const handleClickModifyNickname = () => {
    setIsChangingNickname(true);
  };

  const handleNicknameInput = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.value.length === 13) return;
    setCurNickname(e.target.value);
  };

  const handleClickComplete = () => {
    setIsChangingNickname(false);
    mutate({ nickname: curNickname });
  };

  useEffect(() => {
    setCurNickname(() => nickname);
  }, [nickname]);

  useEffect(() => {
    if (isChangingNickname && profileRef.current !== null) {
      profileRef.current.focus();
    }
  }, [isChangingNickname]);

  return (
    <Info>
      <MenuBox>
        <Column>
          <Label>닉네임</Label>
          <Content>
            <Input
              placeholder={curNickname}
              id="nickname"
              ref={profileRef}
              onChange={handleNicknameInput}
              disabled={!isChangingNickname}
            />
            {isChangingNickname ? (
              <>
                <Button onClick={handleClickComplete}>수정</Button>
                <Button onClick={() => setIsChangingNickname(false)}>취소</Button>
              </>
            ) : (
              <Button onClick={handleClickModifyNickname}>
                <EditIcon />
              </Button>
            )}
          </Content>
        </Column>

        <Column>
          <Label>이메일</Label>
          <Content>
            <Input disabled placeholder={email} />
          </Content>
        </Column>
      </MenuBox>
    </Info>
  );
};

export default MyInfo;

const Info = styled.div`
  display: flex;
  flex-direction: row;

  width: 700px;
  height: 250px;

  border: solid 1px darkGray;
  border-radius: 15px;

  font-family: 'Pretendard-Regular';
  font-size: 17px;

  ${media.mobile`
    width: 100%;
    
    font-size: 27px;
  `}
`;

const MenuBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;

  margin: 0px auto;
`;

const Column = styled.div`
  display: flex;
  align-items: center;

  margin: 15px 0px;
  width: 350px;
  height: 25px;
`;

const Label = styled.label`
  margin: 0px 30px;
  white-space: nowrap;
`;

const Button = styled.button`
  outline: none;

  border: none;
  background-color: inherit;

  cursor: pointer;
  white-space: nowrap;

  &:hover {
    color: ${(props) => props.theme.colors.hoverColor};

    fill: ${(props) => props.theme.colors.hoverColor};
  }
`;

const Content = styled.div`
  display: flex;
`;

const Input = styled.input`
  border: none;
  width: 170px;
  height: 20px;
  font-size: 16px;

  background-color: inherit;

  &::placeholder {
    padding-left: 5px;
  }

  ${media.mobile`
    line-height: 80px;
    font-size:20px;
  `}
`;
