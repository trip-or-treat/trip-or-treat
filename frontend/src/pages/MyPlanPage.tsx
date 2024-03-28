import styled from 'styled-components';
import { useEffect, useState } from 'react';

import MyPlanList from 'src/components/mypage/MyPlanList';
import MyPageTitle from 'src/components/mypage/MyPageTitle';
import SearchToggle from 'src/components/mypage/SearchToggle';
import PlanListCategory from 'src/components/mypage/PlanListCategory';
import Paging from 'src/components/mypage/Pagination/Pagination';

import { CommonContainer } from 'src/styles/CommonContainer';
import { FilterButtonStyle } from 'src/styles/FilterButtonStyle';

import { Plan } from 'src/@types/api/plan';

export const MY_PLANS_DATA = [
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
  {
    planId: 7,
    title: '여행자1 님의 여행계획 14',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18', // 시,분,초 제외
    regions: ['서울', '대전'],
  },
  {
    planId: 6,
    title: '계획13',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['서울', '인천', '대전'],
  },
  {
    planId: 5,
    title: '계획12',
    startDate: '2024-02-15',
    endDate: '2024-02-14',
    createdDate: '2024-03-18',
    regions: ['인천', '대전'],
  },
];

const MyPlanPage = () => {
  // const [planList, setPlanList] = useState<Plan[]>([]); //api로 받아온 데이터
  const [currentPost, setCurrentPost] = useState<Plan[]>([]); // 게시판 목록에 보여줄 게시글
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지 번호

  const postPerPage = 10;
  const indexOfLastPost = currentPage * postPerPage; // 10,20,30...
  const indexOfFirstPost = indexOfLastPost - postPerPage;

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  console.log(currentPage);
  console.log(currentPost);

  // useEffect(() => {
  //   //api 요청해서 계획목록 불러오기. axios.get('baseurl/plans')
  //   .then((response) => {
  //     setPlanList([...response.data].reverse())
  //   })

  //   .catch(function(error) {
  //     console.log(error)
  //   })
  // },[])

  useEffect(() => {
    setCurrentPost(MY_PLANS_DATA.slice(indexOfFirstPost, indexOfLastPost));
  }, [MY_PLANS_DATA, currentPage]);

  return (
    <div>
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
      <ListContainer>
        {currentPost.map((data, idx) => {
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
      </ListContainer>
      <Paging currentPage={currentPage} totalElements={13} setCurrentPage={handlePageChange} />
    </div>
  );
};

export default MyPlanPage;

const ListContainer = styled.div`
  max-height: 48vh;
  min-height: 48vh;
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

const Button = styled.button<{ $isClicked: boolean }>`
  ${FilterButtonStyle};

  height: 25px;
  margin: 0 10px 10px 0;
`;
