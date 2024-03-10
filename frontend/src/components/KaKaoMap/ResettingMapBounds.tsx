import { useEffect, useMemo } from 'react';
import { useMap } from 'react-kakao-maps-sdk';

export const ReSetttingMapBounds = ({ points }: { points: { lat: number; lng: number }[] }) => {
  const map = useMap();
  const bounds = useMemo(() => {
    const boundss = new window.kakao.maps.LatLngBounds();

    points.forEach((point) => {
      boundss.extend(new window.kakao.maps.LatLng(point.lat, point.lng));
    });
    return boundss;
  }, [points]);

  useEffect(() => {
    map.setBounds(bounds);
  }, [points]);

  return <div />;
};
