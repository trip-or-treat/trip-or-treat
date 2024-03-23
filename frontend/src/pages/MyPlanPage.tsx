import styled from 'styled-components';

import MyPlanList from 'src/components/mypage/MyPlanList';
import MyPageTitle from 'src/components/mypage/MyPageTitle';
import SearchToggle from 'src/components/mypage/SearchToggle';
import PlanListCategory from 'src/components/mypage/PlanListCategory';
import { CommonContainer } from 'src/components/mypage/CommonContainer';
import { FilterButtonStyle } from 'src/components/common/FilterButtonStyle';

const MY_PLANS_DATA = [
  {
    planId: 17,
    title: '여행자1 님의 여행계획 14',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18', // 시,분,초 제외
    regions: ['서울', '대전'],
  },
  {
    planId: 16,
    title: '계획13',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천', '대전'],
  },
  {
    planId: 15,
    title: '계획12',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['인천', '대전'],
  },
  {
    planId: 14,
    title: '계획11',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천', '서울'],
  },
  {
    planId: 13,
    title: '계획10',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천', '대전'],
  },
  {
    planId: 12,
    title: '계획9',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천'],
  },
  {
    planId: 11,
    title: '계획8',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천'],
  },
  {
    planId: 10,
    title: '계획7',
    startDate: '2024-02-13',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천'],
  },
  {
    planId: 9,
    title: '계획6',
    startDate: '2024-02-13',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천', '대전'],
  },
  {
    planId: 8,
    title: '계획5',
    startDate: '2024-02-13',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천', '대전'],
  },
];

const MyPlanPage = () => {
  return (
    <Wrapper>
      <MyPageTitle>내 여행계획</MyPageTitle>
      <SearchBox>
        <SearchToggle />
        <Search>검색창입니다.</Search>
        <SearchToggle />
      </SearchBox>
      <FilterBox>
        <Button $isClicked={false}>최근 6개월</Button>
        <Button $isClicked={false}>2024</Button>
      </FilterBox>
      <PlanListCategory />
      {MY_PLANS_DATA.map((data, idx) => {
        return (
          <MyPlanList
            key={data.planId}
            idx={idx + 1}
            title={data.title}
            regions={data.regions}
            startDate={data.startDate}
            endDate={data.endDate}
            createdDate={data.createdDate}
          />
        );
      })}
    </Wrapper>
  );
};

export default MyPlanPage;

const Wrapper = styled.div`
  width: 750px;
  max-height: 100vh;

  overflow-y: auto;
`;

const SearchBox = styled.div`
  display: flex;
  align-items: center;

  margin-bottom: 15px;
`;

const Search = styled.div`
  ${CommonContainer};

  width: 50%;
  margin: 0 10px;

  border-radius: 35px;
  box-shadow: 0px 4px 4px 0px ${(props) => props.theme.colors.lightGrey};
`;

const FilterBox = styled.div`
  display: flex;
`;

// 리팩토링: 필터 재사용
const Button = styled.button<{ $isClicked: boolean }>`
  ${FilterButtonStyle};

  height: 30px;
  margin: 0 10px 10px 0;
`;
