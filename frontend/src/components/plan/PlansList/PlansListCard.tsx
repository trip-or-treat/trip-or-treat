import styled from 'styled-components';

import { ORDER_COLORS } from 'src/constants/color';
import defaultImg from '../../../assets/images/defaultImg.png';

export interface SchedulePlacesType {
  name: string;
  subCategoryName: string;
  imageThumbnail: string;
  placeId: number;
  visitOrder: number;
  memo: string;
  expense: number;
}

interface Props {
  data: SchedulePlacesType;
  idx: number;
  listIdx: number;
}

const PlansListCard = ({ listIdx, idx, data }: Props) => {
  return (
    <Wrapper>
      <OrderBox color={ORDER_COLORS[listIdx]}>{idx + 1}</OrderBox>
      <DescriptionBox>
        <Description>
          <p>{data.name}</p>
          <p>{data.subCategoryName}</p>
        </Description>

        {data.memo && (
          <ul>
            <li>{data.memo}</li>
          </ul>
        )}
      </DescriptionBox>
      <img src={data.imageThumbnail || defaultImg} alt="이미지사진" />
    </Wrapper>
  );
};

export default PlansListCard;

const Wrapper = styled.div`
  display: flex;
  align-items: flex-start;

  width: 100%;
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
  }
`;

const DescriptionBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  width: 60%;
  height: 100px;

  ul {
    margin-bottom: 20px;
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
