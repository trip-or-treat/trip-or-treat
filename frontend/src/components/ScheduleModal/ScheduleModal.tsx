import { useEffect } from 'react';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

import myRegionListAtom from 'src/atoms/myRegionListAtom';
import createScheduleAtom from 'src/atoms/createScheduleAtom';
import dateSelectStateAtom from 'src/atoms/dateSelectStateAtom';

import ModalOverlay from '../common/modal/ModalOverlay';
import Calendar from '../Calender/Calender';
import CommonButton from '../common/CommonButton';
import { CommonButtonBox } from '../common/CommonButton/CommonButton';

const ScheduleModal = () => {
  const myRegionList = useRecoilValue(myRegionListAtom);
  const [disabled, setDisabled] = useRecoilState(createScheduleAtom);
  const setDateSelect = useSetRecoilState(dateSelectStateAtom);

  useEffect(() => {
    setDateSelect(false);
    setDisabled(true);
  }, []);

  return (
    <ModalOverlay>
      <StyledModalLayout>
        <StyledTitle>여행 일정을 선택하실 수 있어요.</StyledTitle>
        <StyledText>
          * 여행일자는 <StyledTextSemiBold>최대 7일까지</StyledTextSemiBold> 설정 가능합니다.
          <br />
          현지 여행 기간
          <StyledTextSemiBold>(여행지 도착 날짜, 여행지 출발 날짜)</StyledTextSemiBold>으로 입력해
          주세요.
        </StyledText>
        <StyledCalendarInner>
          <Calendar />
        </StyledCalendarInner>
        <StyledButtonInner>
          {disabled ? (
            <StyledDisabled>날짜 설정하기</StyledDisabled>
          ) : (
            <Link to={`../region/${myRegionList[0]?.id}`}>
              <CommonButton>날짜 설정하기</CommonButton>
            </Link>
          )}
        </StyledButtonInner>
      </StyledModalLayout>
    </ModalOverlay>
  );
};

export default ScheduleModal;

const StyledModalLayout = styled.div`
  display: flex;
  flex-flow: column;
  align-content: center;
  justify-content: center;
  flex-wrap: wrap;

  width: 761px;
  height: 630px;
  border: none;
  border-radius: 20px;

  background-color: ${(props) => props.theme.colors.whiteFont};
`;

const StyledTitle = styled.h1`
  text-align: center;
  position: relative;
  font-size: 25px;
  margin-bottom: 35px;
  font-family: Pretendard-Bold;
`;

const StyledText = styled.p`
  text-align: center;
  font-family: Pretendard-Thin;
  font-size: 15px;
  margin-bottom: 15px;
  line-height: 1.5;
`;

const StyledTextSemiBold = styled.span`
  font-family: Pretendard-SemiBold;
`;

const StyledCalendarInner = styled.div`
  height: 380px;
`;

const StyledButtonInner = styled.div`
  position: relative;
  text-align: center;
`;

const StyledDisabled = styled(CommonButtonBox)`
  background-color: ${(props) => props.theme.colors.lightGrey};
  color: ${(props) => props.theme.colors.whiteFont};

  cursor: default;
`;
