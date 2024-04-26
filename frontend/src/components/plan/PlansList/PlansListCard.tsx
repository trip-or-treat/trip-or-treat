import styled from 'styled-components';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import { pastPlanAtom } from 'src/atoms/plan';
import placeIdAtom from 'src/atoms/placeIdAtom';
import modalStateAtom from 'src/atoms/modalStateAtom';

import { ORDER_COLORS } from 'src/constants/color';
import { SchedulePlaces, SchedulePlacesDetail } from 'src/@types/api/schedules';

import defaultImg from '../../../assets/images/defaultImg.png';

interface Props {
  data: SchedulePlacesDetail | SchedulePlaces;
  idx: number;
  listIdx: number;
}

const PlansListCard = ({ listIdx, idx, data }: Props) => {
  const isPastPlan = useRecoilValue(pastPlanAtom);
  const setModal = useSetRecoilState(modalStateAtom);
  const setPlaceId = useSetRecoilState(placeIdAtom);

  const openModal = () => {
    setPlaceId({ id: data.placeId, name: data.subCategoryName ? data.subCategoryName : '' });
    setModal(true);
  };

  return (
    <Wrapper>
      <OrderBox color={ORDER_COLORS[listIdx]}>{idx + 1}</OrderBox>
      <DescriptionBox>
        <Description>
          <p>{data.placeName}</p>
          <p>{data.subCategoryName}</p>
        </Description>

        {data.memo && (
          <ul>
            <li>{data.memo}</li>
          </ul>
        )}
        {isPastPlan && <ReviewWrite onClick={openModal}>리뷰 작성</ReviewWrite>}
      </DescriptionBox>
      <img src={data.imageThumbnail || defaultImg} alt="이미지사진" />
    </Wrapper>
  );
};

export default PlansListCard;

const Wrapper = styled.div`
  display: flex;
  align-items: flex-start;

  width: 95%;
  margin-top: 40px;
  padding: 0px 10px;
  border-left: 0.1px solid ${(props) => props.theme.colors.darkGrey};

  font-family: 'Pretendard-Light';
  line-height: 1.1;
  box-sizing: border-box;

  img {
    width: 100px;
    height: 100px;

    border-radius: 10px;
    background: ${(props) => props.theme.colors.skeletonGreyColor};
  }
`;

const DescriptionBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  width: 60%;
  height: 100px;

  ul {
    padding-left: 10px;
    margin: 15px 5px 20px 0px;
    list-style-position: outside;
    list-style-type: disc;
    font-size: 12px;
  }
`;

const Description = styled.div`
  margin-left: 10px;

  p {
    display: flex;
    flex-direction: column;

    &:last-child {
      margin-top: 5px;

      color: ${(props) => props.theme.colors.darkGrey};
      font-size: 12px;
    }
  }
`;

const ReviewWrite = styled.button`
  position: relative;
  text-align: center;

  right: 20px;
  border: 0;
  background-color: transparent;

  font-family: 'Pretendard-Thin';
  text-decoration: underline;
  text-decoration-color: ${(props) => props.theme.colors.darkGrey};
  text-underline-position: under;

  cursor: pointer;
`;

const OrderBox = styled.div<{ color: string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 20px;
  height: 20px;

  border-radius: 50%;
  background-color: ${(props) => props.color};

  color: white;
  font-size: 12px;
`;
