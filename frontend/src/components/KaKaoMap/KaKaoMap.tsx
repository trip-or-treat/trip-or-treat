import styled from 'styled-components';
import { useRecoilValue } from 'recoil';

import { Map, CustomOverlayMap, Polyline } from 'react-kakao-maps-sdk';

import { Regions } from 'src/@types/api/regions';
import { PlaceListTypes } from 'src/@types/api/placeList';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import { ORDER_COLORS } from 'src/constants/color';
import { ReSetttingMapBounds } from './ResettingMapBounds';

interface Props {
  list: Regions[] | PlaceListTypes[];
  curDay?: number;
}

const KaKaoMap = ({ list, curDay }: Props) => {
  const myRegionList = useRecoilValue(myRegionListAtom);
  const markerPositions = list.map((info) => ({
    lat: info.latitude,
    lng: info.longitude,
  }));

  if (list.length === 0) {
    return (
      <Map
        center={{ lat: myRegionList[0].latitude, lng: myRegionList[0].longitude }}
        style={{ width: '100%', height: '100%' }}
        level={7}
      />
    );
  }

  return (
    <Map center={markerPositions[0]} style={{ width: '100%', height: '100%' }} level={10}>
      {list.map((info, idx) => (
        <CustomOverlayMap key={info.id} position={{ lat: info.latitude, lng: info.longitude }}>
          <Marker className="overlay" $bgColor={ORDER_COLORS[(curDay || 1) - 1]}>
            {idx + 1}
          </Marker>
        </CustomOverlayMap>
      ))}
      <Polyline
        path={markerPositions}
        strokeColor={ORDER_COLORS[(curDay || 1) - 1]}
        strokeWeight={2}
        strokeOpacity={0.8}
      />
      {list.length > 1 && <ReSetttingMapBounds points={markerPositions} />}
    </Map>
  );
};

export default KaKaoMap;

const Marker = styled.div<{ $bgColor: string }>`
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  bottom: 6px;
  left: -15px;

  width: 30px;
  height: 30px;

  border-radius: 50%;
  background-color: ${(props) => props.$bgColor};
  color: white;

  &:after {
    content: '';
    position: absolute;
    bottom: -6px;
    left: 50%;
    transform: translateX(-50%);
    border-width: 8px 8px 0;
    border-style: solid;
    border-color: ${(props) => props.$bgColor} transparent transparent;
  }
`;
