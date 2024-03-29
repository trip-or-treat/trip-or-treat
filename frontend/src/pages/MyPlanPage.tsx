import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import MyPlanList from 'src/components/mypage/MyPlanList';
import MyPageTitle from 'src/components/mypage/MyPageTitle';
import DropDown from 'src/components/mypage/DropDown';
import PlanListCategory from 'src/components/mypage/PlanListCategory';
import Paging from 'src/components/mypage/Pagination/Pagination';

// import { CommonContainer } from 'src/styles/CommonContainer';
import { FilterButtonStyle } from 'src/styles/FilterButtonStyle';

import { Plan } from 'src/@types/api/plan';
import { myPlanComingYnAtom, myPlanCreatedYearAtom, myPlanSearchTypeAtom } from 'src/atoms/myPlan';
import useMyPlanList from 'src/hooks/api/useMyPlanList';
import EnterSearch from 'src/components/EnterSearch';

const MyPlanPage = () => {
  const [searchType] = useRecoilValue(myPlanSearchTypeAtom);
  const [comingYn] = useRecoilValue(myPlanComingYnAtom);
  const [createdYear, setCreatedYear] = useRecoilState(myPlanCreatedYearAtom);

  const [keyword, setKeyword] = useState('');
  const [planList, setPlanList] = useState<Plan[]>([]); // api로 받아온 데이터
  const [currentPost, setCurrentPost] = useState<Plan[]>([]); // 게시판 목록에 보여줄 게시글
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지 번호
  const [clickedYear, setClickedYear] = useState(6);

  const token = localStorage.getItem('accessToken') ?? '';
  // 토큰이 없을 때 에러처리

  // 데이터 불러오기
  const { data: myPlanListData } = useMyPlanList({
    pageParam: currentPage,
    searchType,
    keyword: keyword || '',
    comingYn,
    createdYear,
    token,
  });

  useEffect(() => {
    if (myPlanListData instanceof Array) {
      setPlanList(myPlanListData);
    }
  }, [myPlanListData]);

  console.log(myPlanListData);

  // 페이지네이션
  const postPerPage = 10;
  const indexOfLastPost = currentPage * postPerPage; // 10,20,30...
  const indexOfFirstPost = indexOfLastPost - postPerPage;

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  useEffect(() => {
    setCurrentPost(planList.slice(indexOfFirstPost, indexOfLastPost));
  }, [planList, currentPage]);

  console.log(currentPage);
  console.log(currentPost);

  // 작성연도 필터
  const currentYear = new Date().getFullYear();
  const endYear = 2024;
  const createdYearList = [6];

  for (let year = currentYear; year >= endYear; year -= 1) {
    createdYearList.push(year);
  }

  const handleClick = (year: number) => {
    setClickedYear(year);
    setCreatedYear(year);
  };

  return (
    <div>
      <MyPageTitle>내 여행계획</MyPageTitle>
      <SearchBox>
        <DropDownFirst filter="제목,장소" />
        <EnterSearch placeHolder="제목 또는 지역으로 검색해보세요." setKeyword={setKeyword} />
        <DropDownSecond filter="다가오는 여행,지난 여행" />
      </SearchBox>
      <FilterBox>
        {createdYearList.map((year) => (
          <Button key={year} onClick={() => handleClick(year)} $isClicked={clickedYear === year}>
            {year === 6 ? '최근 6개월' : year}
          </Button>
        ))}
      </FilterBox>
      <PlanListCategory />
      <ListContainer>
        {currentPost.map((data, idx) => {
          return (
            <MyPlanList
              key={data.planId}
              idx={10 * (currentPage - 1) + idx + 1}
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

// const Search = styled.div`
//   ${CommonContainer};

//   width: 50%;
//   margin: 0 10px;

//   border-radius: 35px;
//   box-shadow: 0px 4px 4px 0px ${(props) => props.theme.colors.lightGrey};
// `;

const FilterBox = styled.div`
  display: flex;
`;

const Button = styled.button<{ $isClicked: boolean }>`
  ${FilterButtonStyle};

  height: 25px;
  margin: 0 10px 10px 0;
`;

const DropDownFirst = styled(DropDown)`
  width: 20%;
`;

const DropDownSecond = styled(DropDown)`
  width: 25%;
`;
