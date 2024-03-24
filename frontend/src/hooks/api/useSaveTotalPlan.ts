import { SchedulesType } from 'src/@types/api/schedules';
import { api } from 'src/api';

export const useSaveTotalPlan = async (payload: SchedulesType) => {
  const token = localStorage.getItem('accessToken');
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await api.post('/plans', payload, { headers });
  return response;
};
