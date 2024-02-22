import StepNavLinkButton from 'src/components/StepNavLinkButton';

const StepOnePage = () => {
  return (
    <>
      <StepNavLinkButton type="ENABLE_ONLY" path="/result">
        계획 저장
      </StepNavLinkButton>

      <StepNavLinkButton type="ENABLE_AND_DISABLE" path="/place/1">
        다음
      </StepNavLinkButton>
    </>
  );
};

export default StepOnePage;
