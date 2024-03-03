export interface RegionsMoreInformation {
  id: number;
  name: string;
  imageOrigin: string;
  overview: string;
  latitude: number;
  longitude: number;
  recommendedPlaces: {
    id: number;
    name: string;
    overview: string;
    imageThumbnail: string;
  }[];
}
