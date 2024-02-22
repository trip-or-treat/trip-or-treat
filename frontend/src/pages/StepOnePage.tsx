import StepNavLinkButton from 'src/components/StepNavLinkButton';

const StepOnePage = () => {
  return (
    <>
      <StepNavLinkButton type="CHANGE" path="/result">
        계획 저장
      </StepNavLinkButton>

      <StepNavLinkButton type="NOT_CHANGE" path="/place/1">
        다음
      </StepNavLinkButton>
    </>
  );
};

export default StepOnePage;
