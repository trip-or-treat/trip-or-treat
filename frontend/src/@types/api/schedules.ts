export interface SchedulesType {
  title: string;
  startDate: string;
  endDate: string;
  schedules: {
    date: string;
    schedulePlaces: {
      placeId: number;
      visitOrder: number;
      memo: string;
      expense: number;
    }[];
  }[];
}
