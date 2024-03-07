import { SchedulesType } from 'src/@types/api/schedules';
import { api } from 'src/api';

export const useSaveTotalPlan = async (payload: SchedulesType) => {
  const response = await api.post('/plans', payload);
  return response;
};
