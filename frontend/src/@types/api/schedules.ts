export interface SchedulePlaces {
  memo: string;
  expense: number;
  placeId: number;
  placeName?: string;
  visitOrder: number;
  imageThumbnail?: string;
  subCategoryName?: string;
}

export interface SchedulePlacesDetail extends SchedulePlaces {
  memo: string;
  expense: number;
  placeId: number;
  regionId: number;
  latitude: number;
  longitude: number;
  placeName: string;
  visitOrder: number;
  contentTypeId: number;
  imageThumbnail: string;
  subCategoryName: string;
  schedulePlaceId?: number;
}

export interface SchedulesType {
  title: string;
  startDate: string;
  endDate: string;
  schedules: {
    date: string;
    scheduleId?: number;
    schedulePlaces: SchedulePlaces[] | SchedulePlacesDetail[];
  }[];
}
