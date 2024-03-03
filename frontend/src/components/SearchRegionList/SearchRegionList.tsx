import styled from 'styled-components';

import RegionCard from '../common/RegionCard';

const data = [
  {
    id: 11,
    name: '울산',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
  {
    id: 12,
    name: '부산',
    imageOrigin: '',
    imageThumbnail: '',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
  {
    id: 13,
    name: '포항',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
  {
    id: 14,
    name: '서울',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
  {
    id: 15,
    name: '부산',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
  {
    id: 16,
    name: '포항',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
  {
    id: 17,
    name: '서울',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
  {
    id: 18,
    name: '부산',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
  {
    id: 19,
    name: '포항',
    imageOrigin:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    latitude: 123.1231231231,
    longitude: 35.1231231231,
  },
];

const SearchRegionList = () => {
  return (
    <Wrapper>
      {data.map((item) => (
        <RegionCard key={item.id} item={item} type="ADD_BUTTON" />
      ))}
    </Wrapper>
  );
};

export default SearchRegionList;

const Wrapper = styled.div`
  overflow-y: auto;

  width: 100%;
  height: 288px;
  padding: 0px 20px;
  margin-bottom: 5px;

  box-sizing: border-box;
`;
