import { SchedulePlacesDetail, SchedulesType } from './schedules';

interface Schedules {
  scheduleId: number;
  date: string;
  schedulePlaces: SchedulePlacesDetail[];
}

export interface SchedulesTypeWithRegions extends SchedulesType {
  regions: {
    regionIds: number[];
    regionNames: string[];
  };
  code: string;
  planId: number;
  schedules: Schedules[];
}
