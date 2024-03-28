export interface PlaceListTypes {
  placeId: number;
  name: string;
  imageThumbnail: string;
  subCategoryName: string;
  latitude: number;
  longitude: number;
  contentTypeId: number;
  regionId?: string;
  memo?: string;
  expense?: number;
}
