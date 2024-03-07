import styled from 'styled-components';
import { useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';

import theme from 'src/styles/theme';
import totalPlanAtom from 'src/atoms/totalPlanAtom';
import myRegionListAtom from 'src/atoms/myRegionListAtom';
import { useSaveTotalPlan } from 'src/hooks/api/useSaveTotalPlan';
import { SchedulesType } from 'src/@types/api/schedules';

import ModalOverlay from '../common/modal/ModalOverlay';
import CloseButton from '../common/modal/CloseButton';
import { StyledButton } from '../common/modal/LinkButton/LinkButton';

interface Props {
  onClose: () => void;
}

const ConfirmSaveModal = ({ onClose }: Props) => {
  const totalPlan = useRecoilValue(totalPlanAtom);
  const myRegionList = useRecoilValue(myRegionListAtom);
  const navigate = useNavigate();
  const schedules = totalPlan.map(({ date, items }) => ({
    date,
    schedulePlaces: items.map((item, idx) => ({
      placeId: item.id,
      visitOrder: idx + 1,
      memo: '메모',
      expense: 0,
    })),
  }));

  const { mutate } = useMutation({
    mutationFn: (payload: SchedulesType) => useSaveTotalPlan(payload),
    onSuccess: () => {
      alert('성공적으로 저장이 완료되었습니다!');
      navigate('/myPage');
    },
    onError: () => {
      alert('저장이 실패했습니다!');
      onClose();
    },
  });

  const handleSave = () => {
    const result = {
      title: `${myRegionList.map((data) => data.name).join('-')} 여행코스`,
      startDate: totalPlan[0].date,
      endDate: totalPlan[totalPlan.length - 1].date,
      schedules,
    };
    mutate(result);
  };

  return (
    <ModalOverlay>
      <StyledModalLayout>
        <StyledModalText>정말 저장하시겠어요?</StyledModalText>
        <StyledButtonInner>
          <LinkButton onClick={handleSave} color={theme.colors.mainColor}>
            저장
          </LinkButton>
          <CloseButton onClick={onClose} color={theme.colors.darkGrey}>
            닫기
          </CloseButton>
        </StyledButtonInner>
      </StyledModalLayout>
    </ModalOverlay>
  );
};

export default ConfirmSaveModal;

export const StyledModalLayout = styled.div`
  display: flex;
  flex-flow: column;

  width: 356px;
  height: 179px;

  border: none;
  border-radius: 10px;

  background-color: ${(props) => props.theme.colors.whiteFont};
  box-shadow: 2px 2px 3px 0px ${(props) => props.theme.colors.darkGrey};
`;

export const StyledModalText = styled.h1`
  display: flex;
  justify-content: center;
  align-self: center;
  align-items: center;

  width: 272px;
  height: 109px;
  margin: 15px 15px 0px 15px;

  color: ${(props) => props.theme.colors.blackFont};
  font-family: 'Pretendard-SemiBold';
  font-size: 18px;
  line-height: 1.5;
`;

export const StyledButtonInner = styled.div`
  display: flex;
  justify-content: center;
`;

const LinkButton = styled.button<{ color: string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  ${StyledButton}

  background-color: ${(props) => props.color};
  color: ${(props) => props.theme.colors.whiteFont};
`;
