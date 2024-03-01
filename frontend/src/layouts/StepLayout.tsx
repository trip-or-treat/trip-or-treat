import { Outlet, useParams } from 'react-router-dom';
import styled from 'styled-components';

import Nav from 'src/components/common/Nav';
import StepNavBar from 'src/components/StepNavBar';
import { useRecoilState } from 'recoil';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import { useEffect } from 'react';

const MY_TRIP_DATA = [
  {
    id: 1,
    name: '서울',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
];

const StepLayout = () => {
  const { regionId } = useParams();
  const [myRegionItemList, setMyRegionItemListAtom] = useRecoilState(myRegionListAtom);

  useEffect(() => {
    const mainRegion = MY_TRIP_DATA.find((item) => item.id === Number(regionId));
    if (mainRegion && myRegionItemList.length === 0) setMyRegionItemListAtom([mainRegion]);
  }, [regionId]);

  return (
    <>
      <Nav />
      <StepNavBar />
      <Main>
        <Outlet />
      </Main>
    </>
  );
};

export default StepLayout;

const Main = styled.main`
  position: fixed;
  margin-top: ${(props) => props.theme.height.topNavHeight};
  margin-left: ${(props) => props.theme.width.leftNavWidth};
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;
