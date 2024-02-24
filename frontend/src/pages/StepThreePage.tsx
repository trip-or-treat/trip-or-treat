import styled from 'styled-components';
import RegionCategory from 'src/components/RegionCategory';

const REGION_DATA = [
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
  {
    id: 2,
    name: '부산',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231232,
    longitude: 35.1231231231,
  },
  {
    id: 3,
    name: '포항',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231232,
    longitude: 35.1231231231,
  },
];

const StepThreePage = () => {
  return (
    <Wrapper>
      <FirstContents>
        <RegionCategory data={REGION_DATA} />
      </FirstContents>
      <SecondContents>Day</SecondContents>
      <Map>map</Map>
    </Wrapper>
  );
};

export default StepThreePage;

const Wrapper = styled.div`
  display: flex;

  width: calc(100vw - ${(props) => props.theme.width.leftNavWidth});
  height: calc(100vh - ${(props) => props.theme.height.topNavHeight});
`;

const FirstContents = styled.div`
  width: 30%;
  height: inherit;
  padding: 20px;

  box-sizing: border-box;
`;

const SecondContents = styled.div`
  width: 30%;
  height: inherit;

  background-color: antiquewhite;
`;

const Map = styled.div`
  width: 40%;
  height: inherit;

  background-color: darkgoldenrod;
`;
