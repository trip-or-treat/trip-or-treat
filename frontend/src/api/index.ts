import axios, { InternalAxiosRequestConfig, AxiosResponse } from 'axios';

import { BASE_URL } from 'src/constants/api';
import { LOGIN } from 'src/constants/login';

export const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

export const loginApi = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

loginApi.interceptors.request.use((config): InternalAxiosRequestConfig => {
  const token = localStorage.getItem(LOGIN.TOKEN);
  const customConfig = { ...config };

  customConfig.headers.Authorization = `${LOGIN.BEARER}${token}`;

  return customConfig;
});

loginApi.interceptors.response.use((response): AxiosResponse => {
  if (response.headers.authorization) {
    localStorage.setItem(LOGIN.TOKEN, response.headers.authorization.split(LOGIN.BEARER)[1]);
  }
  if (response.data.status === 401) {
    alert('로그인을 다시 시도해주세요.');
    localStorage.removeItem(LOGIN.TOKEN);
    window.location.href = '/';
  }
  return response;
});
