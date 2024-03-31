import { SchedulesType } from 'src/@types/api/schedules';
import { loginApi } from 'src/api';
import { END_POINTS } from 'src/constants/api';

export const usePatchTotalPlan = async (id: number, payload: SchedulesType) => {
  const token = localStorage.getItem('accessToken');
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await loginApi.patch(`${END_POINTS.PLAN}/${id}`, payload, { headers });
  return response;
};
