import axios from 'axios';
import { BASE_URL } from 'src/constants/api';

export const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});
