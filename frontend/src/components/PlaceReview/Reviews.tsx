import { styled } from 'styled-components';

import { ReactComponent as StarFilled } from 'src/assets/svgs/starFilled.svg';
import { ReactComponent as HoneyPot } from 'src/assets/svgs/honeyPot.svg';

const data = [
  {
    id: 1,
    nickname: 'user1',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    content:
      '오랜만에 가족이랑 나들이를 왔습니다 아주 산좋고 공기좋고 물좋은 곳..! 추천합니다!오랜만에 가족이랑 오랜만에 가족이랑 나들이를 왔습니다 아주 산좋고 공기좋고 물좋은 곳..! 추천합니다!나들이를 왔습니다 아주 산좋고 공기좋고 물좋은 곳..! 추천합니다!',
    tip: '개꿀마!',
    score: 4,
    createdDate: '2023-03-01',
  },
  {
    id: 2,
    nickname: 'user2user1',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    content: '소금빵 맛집dd ',
    tip: '',
    score: 3,
    createdDate: '2023-03-02',
  },
  {
    id: 3,
    nickname: 'user2',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    content: '소금빵 맛집 ',
    tip: '소금빵 꼭 시켜야함',
    score: 3,
    createdDate: '2023-03-02',
  },
  {
    id: 4,
    nickname: 'user24',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    content: '소금빵 맛집zz ',
    tip: '소금빵 꼭 시켜야함',
    score: 5,
    createdDate: '2023-03-02',
  },
  {
    id: 5,
    nickname: 'user26',
    imageThumbnail:
      'https://plus.unsplash.com/premium_photo-1661948404806-391a240d6d40?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fCVFRCU5NSU5QyVFQSVCNSVBRCUyMCVFQyU5NyVBQyVFRCU5NiU4OXxlbnwwfHwwfHx8MA%3D%3D',
    content: '소금빵 맛집!! ',
    tip: '소금빵 꼭 시켜야함',
    score: 1,
    createdDate: '2023-03-02',
  },
];

const Reviews = () => {
  const scoreArr = (score: number) =>
    Array.from({ length: score }).map((_, index) => `star${index}`);

  return (
    <Inner>
      {data.map((el) => (
        <ReviewInner key={el.id}>
          <Review>
            <Info>
              <ProfileImg src={el.imageThumbnail} alt={el.nickname} />
              <Nickname>{el.nickname}</Nickname>
              {scoreArr(el.score).map((key) => (
                <StarFilled key={`${el.id}${key}`} style={{ width: '17px' }} />
              ))}
            </Info>
            {el.createdDate}
          </Review>
          <Content>{el.content}</Content>
          {el.tip !== '' && (
            <Tip>
              <HoneyPot /> {el.tip}
            </Tip>
          )}
        </ReviewInner>
      ))}
    </Inner>
  );
};

export default Reviews;

const Inner = styled.div`
  height: 85%;
  padding: 20px;

  overflow: auto;

  > * {
    font-family: 'Pretendard-Regular';
    color: ${(props) => props.theme.colors.blackFont};
  }
`;

const ReviewInner = styled.div`
  height: 30%;
  padding: 10px;
  margin-bottom: 15px;

  border-radius: 10px;

  box-shadow: 0px 2px 3px 0px darkgray;
`;

const Review = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  margin: 10px;
`;

const Info = styled.div`
  display: flex;
  align-items: center;
`;

const Nickname = styled.div`
  margin-right: 10px;
`;

const ProfileImg = styled.img`
  width: 30px;
  height: 30px;
  margin-right: 10px;

  border: none;
  border-radius: 50%;
`;

const Content = styled.div`
  height: 35%;
  padding: 10px;

  line-height: 1.3;

  overflow: auto;
`;

const Tip = styled.div`
  display: flex;
  align-items: center;

  margin-top: 5px;
  padding: 10px;

  border-top: 0.1px solid;
  border-color: ${(props) => props.theme.colors.lightGrey};
`;
