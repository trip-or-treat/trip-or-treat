import styled from 'styled-components';

import { PlaceListTypes } from 'src/@types/api/placeList';
import defaultImg from '../../assets/images/defaultImg.png';

const INDEX_COLOR = ['#ff595e', '#ff5d8f', '#ffea00', '#70e000', '#1982c4', '#274c77', '#6a4c93'];

interface Props {
  data: PlaceListTypes;
  idx: number;
  listIdx: number;
}

const PlansListCard = ({ listIdx, idx, data }: Props) => {
  return (
    <Wrapper>
      <OrderBox color={INDEX_COLOR[listIdx]}>{idx + 1}</OrderBox>
      <Description>
        <p>{data.name}</p>
        <p>{data.subCategoryName}</p>
      </Description>
      <img src={data.imageThumbnail || defaultImg} alt="이미지사진" />
    </Wrapper>
  );
};

export default PlansListCard;

const Wrapper = styled.div`
  display: flex;
  align-items: flex-start;

  margin-top: 25px;
  padding: 0px 20px;

  border-left: 0.1px solid ${(props) => props.theme.colors.darkGrey};

  font-family: 'Pretendard-Light';
  line-height: 1.1;

  img {
    width: 100px;
    height: 100px;

    border-radius: 10px;
  }
`;

const Description = styled.div`
  width: 50%;
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
