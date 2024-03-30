import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';

import MyPlanList from 'src/components/mypage/MyPlanList';
import MyPageTitle from 'src/components/mypage/MyPageTitle';
import DropDown from 'src/components/mypage/DropDown';
import PlanListCategory from 'src/components/mypage/PlanListCategory';
import Paging from 'src/components/mypage/Pagination/Pagination';
import MyPlanSearch from 'src/components/mypage/MyPlanSearch';
import EmptyBox from 'src/components/common/EmptyBox';

// import { CommonContainer } from 'src/styles/CommonContainer';
import { FilterButtonStyle } from 'src/styles/FilterButtonStyle';
import useMyPlanList from 'src/hooks/api/useMyPlanList';

import {
  myPlanComingYnAtom,
  myPlanCreatedYearAtom,
  myPlanCurrentPageAtom,
  myPlanCurrentPostAtom,
  myPlanSearchTypeAtom,
} from 'src/atoms/myPlan';

interface MyPlanData {
  data: {
    data: TotalMyPlanData;
  };
  isLoading: boolean;
  isError: boolean;
}

interface TotalMyPlanData {
  contents: {
    planId: number;
    title: string;
    startDate: string;
    endDate: string;
    createdDate: string;
    regions: string[];
  }[];
  page: number;
  totalPages: number;
  prev: boolean;
  next: boolean;
  startPage: number;
  endPage: number;
}

const MyPlanPage = () => {
  const [searchType, setSearchType] = useRecoilState(myPlanSearchTypeAtom);
  const [comingYn, setComingYn] = useRecoilState(myPlanComingYnAtom);
  const [createdYear, setCreatedYear] = useRecoilState(myPlanCreatedYearAtom); // 작성연도
  const [currentPage, setCurrentPage] = useRecoilState(myPlanCurrentPageAtom); // 현재 페이지 번호
  const [currentPost, setCurrentPost] = useRecoilState(myPlanCurrentPostAtom); // 게시판 목록에 보여줄 게시글

  const [keyword, setKeyword] = useState('');

  // 데이터 불러오기
  const {
    data: myPlanData,
    isError,
    isLoading,
  }: MyPlanData = useMyPlanList({
    pageParam: currentPage,
    searchType,
    keyword: keyword || '',
    comingYn,
    createdYear,
  });

  if (isError) return null;
  console.log(myPlanData?.data);

  useEffect(() => {
    if (!isLoading) {
      setCurrentPost(myPlanData.data.contents);
    }
  }, [myPlanData]);

  // 페이지네이션

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
    console.log('currentPage:', page);
  };
  console.log(currentPost);

  // 작성연도 필터
  const currentYear = new Date().getFullYear();
  const endYear = 2023;
  const createdYearList = [6];

  for (let year = currentYear; year >= endYear; year -= 1) {
    createdYearList.push(year);
  }

  const handleClick = (year: number) => {
    setCreatedYear(year);
    console.log(createdYear);
  };

  useEffect(() => {
    setCreatedYear(null);
  }, []);

  const searchTypeObj = {
    제목: 'T',
    지역: 'R',
  };

  const comingYnObj = {
    '다가오는 여행': 'Y',
    '지난 여행': 'N',
  };

  return (
    <div>
      <MyPageTitle>내 여행계획</MyPageTitle>
      <SearchBox>
        <DropDownFirst filter={searchTypeObj} setParams={setSearchType} />
        <MyPlanSearch placeHolder="제목 또는 지역으로 검색해보세요." setKeyword={setKeyword} />
        <DropDownSecond filter={comingYnObj} setParams={setComingYn} />
      </SearchBox>
      <FilterBox>
        {createdYearList.map((year) => (
          <Button key={year} onClick={() => handleClick(year)} $isClicked={createdYear === year}>
            {year === 6 ? '최근 6개월' : year}
          </Button>
        ))}
      </FilterBox>
      <PlanListCategory />
      <ListContainer>
        {currentPost.length === 0 && <EmptyBox>계획 목록이 없습니다.</EmptyBox>}
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
              planId={data.planId}
            />
          );
        })}
      </ListContainer>
      <Paging currentPage={currentPage} totalElements={55} setCurrentPage={handlePageChange} />
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

const FilterBox = styled.div`
  display: flex;
`;

const Button = styled.button<{ $isClicked: boolean }>`
  ${FilterButtonStyle};

  height: 25px;
  margin: 0 10px 10px 0;
`;

const DropDownFirst = styled(DropDown)`
  width: 40px;
`;

const DropDownSecond = styled(DropDown)`
  width: 60px;
`;
