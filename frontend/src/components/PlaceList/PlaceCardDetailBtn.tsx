import { useSetRecoilState } from 'recoil';
import styled from 'styled-components';

import modalStateAtom from 'src/atoms/modalStateAtom';
import placeIdAtom from 'src/atoms/placeIdAtom';
import { PlaceListTypes } from 'src/@types/api/placeList';

import defaultimg from '../../assets/images/defaultImg.png';

interface Props {
  placeCardItem: PlaceListTypes;
}

const PlaceCardDetailBtn = ({ placeCardItem }: Props) => {
  const setModal = useSetRecoilState(modalStateAtom);
  const setPlace = useSetRecoilState(placeIdAtom);

  const handleClickModal = () => {
    setPlace({ id: placeCardItem.id, name: placeCardItem.subCategoryName });
    setModal(true);
  };

  return (
    <DetailButton onClick={handleClickModal}>
      <div>
        <ThumbnailImg src={placeCardItem.imageThumbnail || defaultimg} />
      </div>
      <RegionNameBox>
        <p>{placeCardItem.name}</p>
        <p>{placeCardItem.subCategoryName}</p>
      </RegionNameBox>
    </DetailButton>
  );
};

export default PlaceCardDetailBtn;

const DetailButton = styled.button`
  display: flex;
  align-items: center;
  width: 70%;
  border: none;
  background-color: inherit;
  cursor: pointer;
`;

const ThumbnailImg = styled.img`
  width: 35px;
  height: 35px;
  margin-right: 15px;
  border-radius: 50%;
  object-fit: cover;
`;

const RegionNameBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  color: ${(props) => props.theme.colors.blackFont};

  p {
    font-family: 'Pretendard-Medium';
    font-size: 17px;

    &:first-child {
      width: 150px;
      height: 20px;

      margin-bottom: 3px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      text-align: left;
    }

    &:last-child {
      margin-bottom: 5px;
      font-size: 11px;
      color: grey;
    }
  }
`;
