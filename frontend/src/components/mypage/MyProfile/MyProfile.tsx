import React, { useRef, useState, createRef } from 'react';
import Cropper, { ReactCropperElement } from 'react-cropper';
import { useMutation } from '@tanstack/react-query';
import styled from 'styled-components';
import media from 'src/styles/media';
import 'cropperjs/dist/cropper.css';

import { profileChangeFetcher } from 'src/api/user';

import { ReactComponent as Close } from 'src/assets/svgs/close.svg';
import ModalOverlay from 'src/components/common/modal/ModalOverlay';

const MyProfile = ({ imageThumbnail }: { imageThumbnail: string }) => {
  const inputRef = useRef<HTMLInputElement | null>(null);
  const cropperRef = createRef<ReactCropperElement>();
  const [fileName, setFileName] = useState<string>('');
  const [userImg, setUserImg] = useState<string>(imageThumbnail);
  const [previewImg, setPreviewImg] = useState<string>('');
  const [isPreview, setPreview] = useState<boolean>(false);

  const onPreview = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) {
      return;
    }
    previewFile(e.target.files[0]);
    setFileName(e.target.files[0].name);
  };

  const previewFile = (file: File) => {
    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setPreviewImg(reader.result as string);
      setPreview(true);
    };
  };

  const onClick = () => {
    if (!inputRef.current) {
      return;
    }
    inputRef.current.click();
  };

  const onClose = () => {
    setPreview(false);
  };

  const dataURLtoFile = (dataURL: string): File | undefined => {
    const arr = dataURL.split(',');
    const matches = arr[0].match(/:(.*?);/);
    const mime = matches && matches.length >= 2 ? matches[1] : undefined;

    if (!mime) {
      cropperRef.current?.cropper.reset();
      return undefined;
    }

    const bstr = atob(arr[1]);
    let n = bstr.length;
    const u8arr = new Uint8Array(n);

    while (n !== -1) {
      u8arr[n] = bstr.charCodeAt(n);
      n -= 1;
    }

    return new File([u8arr], fileName, { type: mime });
  };

  const changeMutation = useMutation({ mutationFn: profileChangeFetcher });

  const cropImgUpload = () => {
    const data = cropperRef.current?.cropper.getCroppedCanvas().toDataURL();
    const reader = new FileReader();
    const cropImgFile = dataURLtoFile(data as string) as File;

    changeMutation.mutate(cropImgFile, {
      onSuccess: () => {
        reader.readAsDataURL(cropImgFile);
        reader.onloadend = () => {
          setUserImg(reader.result as string);
        };

        alert('프로필 변경이 완료되었습니다!');
        onClose();
      },

      onError: () => {
        alert('프로필 변경이 실패했습니다!');
        onClose();
      },
    });
  };

  return (
    <ProfileBox>
      {isPreview ? (
        <ModalOverlay>
          <PreviewInner>
            <StyledIcon>
              <Close onClick={onClose} />
            </StyledIcon>
            <PreviewTitle>이미지 미리보기</PreviewTitle>
            <Cropper
              ref={cropperRef}
              src={previewImg}
              alt="user Img"
              style={{ height: '400px', width: '400px', objectFit: 'cover' }}
              zoomTo={0.5}
              initialAspectRatio={1}
              preview=".img-preview"
              viewMode={1}
              minCropBoxHeight={10}
              minCropBoxWidth={10}
              background={false}
              autoCropArea={1}
              checkOrientation={false}
            />
            <PreviewBtn onClick={cropImgUpload}>이미지 변경</PreviewBtn>
          </PreviewInner>
        </ModalOverlay>
      ) : (
        <ProfileImg src={userImg} alt="이미지를 불러오지 못했습니다." />
      )}
      <Input type="file" accept=".png, .jpg, .jpeg" ref={inputRef} onChange={onPreview} />
      <ChangeProfile onClick={onClick}>프로필 변경</ChangeProfile>
    </ProfileBox>
  );
};

export default MyProfile;

const ProfileBox = styled.div`
  display: flex;
  flex-direction: column;

  width: 700px;
  margin-bottom: 20px;

  ${media.mobile`
    width: 100%;
    margin-bottom: 70px;
  `}
`;

const ProfileImg = styled.img<{ src: string }>`
  width: 130px;
  height: 130px;
  padding: 10px;
  margin: 0 auto;

  border: solid 0.7px darkGray;
  border-radius: 50%;

  font-family: 'Pretendard-Regular';
  font-size: 20px;
  color: ${(props) => props.theme.colors.darkGrey};

  ${media.mobile`
    width: 200px;
    height: 200px;
  `}
`;

const ChangeProfile = styled.button`
  width: 100px;
  height: 25px;
  margin: 20px auto 0 auto;

  border: none;
  background-color: white;

  font-family: 'Pretendard-Regular';
  font-size: 15px;
  color: #3d6eff;

  cursor: pointer;

  &:hover {
    color: #0741ed;
  }
`;

const Input = styled.input`
  display: none;
`;

const PreviewInner = styled.div`
  width: 440px;
  height: 540px;
  display: flex;
  padding: 15px;
  flex-direction: column;
  background: ${(props) => props.theme.colors.whiteFont};
  border-radius: 10px;
  align-items: center;
`;

const PreviewTitle = styled.div`
  width: 100%;
  height: 10%;
  font-family: 'Pretendard-Regular';
  font-size: 20px;
  color: ${(props) => props.theme.colors.blackFont};
  justify-content: center;
  display: flex;
  align-items: center;
  margin-bottom: 20px;
`;

const PreviewBtn = styled.button`
  width: 70%;
  border: 0;
  height: 10%;
  border-radius: 15px;
  background-color: yellow;
  margin-top: 20px;
  font-size: 20px;
  font-family: 'Pretendard-Regular';
`;

const StyledIcon = styled.div`
  position: relative;
  width: 100%;
  text-align: right;

  cursor: pointer;
`;
