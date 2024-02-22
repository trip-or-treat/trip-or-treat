import StepNavLink from 'src/components/StepNavLink';

const StepOnePage = () => {
  return (
    <>
      <StepNavLink type="CHANGE" path="/result">
        계획 저장
      </StepNavLink>

      <StepNavLink type="NOT_CHANGE" path="/place/1">
        다음
      </StepNavLink>
    </>
  );
};

export default StepOnePage;
