import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { styled } from 'styled-components';
import media from 'src/styles/media';

import { ReactComponent as StarFilled } from 'src/assets/svgs/starFilled.svg';
import { ReactComponent as Pencil } from 'src/assets/svgs/pencil.svg';

import { editReviewAtom } from 'src/atoms/review';

import ReviewInput from './ReviewInput';
import Reviews from './Reviews';

const PlaceReview = ({ currentId }: { currentId: number }) => {
  const [isReviewInput, setReviewInput] = useState(false);
  const [isReviews, setReviews] = useState(true);
  const [isTitle, setTitle] = useState(true);
  const isEdit = useRecoilValue(editReviewAtom);

  const prevPage = localStorage.getItem('prevPage');

  useEffect(() => {
    const isOnly = prevPage?.split('/')[1] === 'place';

    if (isOnly) {
      setTitle(false);
    }
  }, []);

  useEffect(() => {
    if (isEdit) {
      alert('수정이 완료되었습니다.');
      setTitle(false);
      onReviews();
    }
  }, [isEdit]);

  const onReviewInput = () => {
    setReviewInput(true);
    setReviews(false);
  };

  const onReviews = () => {
    setReviews(true);
    setReviewInput(false);
  };

  return (
    <Wrapper>
      <Nav>
        {isTitle && (
          <>
            <Title
              onClick={onReviewInput}
              style={isReviewInput ? { borderBottom: `1px solid #d9d9d9` } : {}}
            >
              리뷰 작성 <Pencil />
            </Title>
            <Title
              onClick={onReviews}
              style={isReviews ? { borderBottom: '1px solid #d9d9d9' } : {}}
            >
              리뷰 <StarFilled />
            </Title>
          </>
        )}
      </Nav>
      {isReviewInput && <ReviewInput currentId={currentId} />}
      {isReviews && <Reviews currentId={currentId} />}
    </Wrapper>
  );
};

export default PlaceReview;

const Wrapper = styled.div`
  position: relative;
  left: 20px;

  width: 489px;
  height: 580px;

  ${media.mobile`
    left: 0px;
    border-top: 1px solid #D9D9D9;
    margin-top: 30px;
    height: 415px;
  `}
`;

const Nav = styled.div`
  display: flex;
  position: relative;
  justify-content: space-around;
  align-items: center;

  height: 10%;
  margin-top: 15px;

  ${media.mobile`
    &:empty {
      display: none;
    }
  `}
`;

const Title = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 50%;
  padding: 10px;

  border: none;
  background-color: transparent;

  text-align: center;
  font-size: 20px;
  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-SemiBold';

  outline: none;
  cursor: pointer;

  > * {
    margin-left: 10px;
  }
`;
