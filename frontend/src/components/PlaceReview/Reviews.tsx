import { styled } from 'styled-components';
import media from 'src/styles/media';

import { ReactComponent as StarFilled } from 'src/assets/svgs/starFilled.svg';
import { ReactComponent as HoneyPot } from 'src/assets/svgs/honeyPot.svg';

import { useReview } from 'src/hooks/api/useReviews';
import { Review } from 'src/@types/api/review';

import Loading from '../common/Loading';

interface ReviewData {
  data: {
    data: Review[];
  };
  isLoading: boolean;
  isError: boolean;
}

const Reviews = ({ currentId }: { currentId: number }) => {
  const scoreArr = (score: number) =>
    Array.from({ length: score }).map((_, index) => `star${index}`);

  const { data: reviewsApi, isLoading, isError }: ReviewData = useReview(currentId);

  if (isLoading) {
    return <Loading type="SMALL" />;
  }
  if (isError) {
    return (
      <Inner>
        <EmptyContent>데이터를 불러올 수 없습니다.</EmptyContent>
      </Inner>
    );
  }

  return (
    <Inner>
      {reviewsApi.data.length === 0 ? (
        <EmptyContent>여행자님의 리뷰를 기다리고 있습니다 🙇🏻</EmptyContent>
      ) : (
        reviewsApi.data.map((data) => (
          <ReviewInner key={data.id}>
            <Title>
              <Info>
                <ProfileImg src={data.imageThumbnail} alt={data.nickname} />
                <Nickname>{data.nickname}</Nickname>
                {scoreArr(data.score).map((key) => (
                  <StarFilled key={`${data.id}${key}`} style={{ width: '17px' }} />
                ))}
              </Info>
              <Date>{data.createdDate.split('T')[0]}</Date>
            </Title>
            <Content>{data.content}</Content>
            {data.tip !== '' && (
              <Tip>
                <HoneyPot /> {data.tip}
              </Tip>
            )}
          </ReviewInner>
        ))
      )}
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

  ${media.mobile`
    height: 65%;
  `}
`;

const ReviewInner = styled.div`
  height: auto;
  padding: 10px;
  margin-bottom: 15px;

  border-radius: 10px;

  box-shadow: 2px 2px 5px 0px ${(props) => props.theme.colors.darkGrey};
`;

const Title = styled.div`
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

const Date = styled.div`
  font-size: 13px;
  color: ${(props) => props.theme.colors.darkGrey};
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

const EmptyContent = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100%;
`;
