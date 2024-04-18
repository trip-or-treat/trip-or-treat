import { loginApi } from '.';

export const profileChangeFetcher = async (file: File) => {
  const fromData = new FormData();
  fromData.append('profileImageFile', file);

  const config = {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  };

  const data = await loginApi.put('/user/profile', fromData, config);

  return data.data;
};
