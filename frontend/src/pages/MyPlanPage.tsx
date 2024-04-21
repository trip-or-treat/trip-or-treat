import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';

import media from 'src/styles/media';

import MyPlanList from 'src/components/mypage/MyPlanList';
import MyPageTitle from 'src/components/mypage/MyPageTitle';
import DropDown from 'src/components/mypage/DropDown';
import PlanListCategory from 'src/components/mypage/PlanListCategory';
import Paging from 'src/components/mypage/Pagination/Pagination';
import MyPlanSearch from 'src/components/mypage/MyPlanSearch';
import EmptyBox from 'src/components/common/EmptyBox';

import { Plan } from 'src/@types/api/plan';
import { FilterButtonStyle } from 'src/styles/FilterButtonStyle';
import useMyPlanList from 'src/hooks/api/useMyPlanList';

import {
  myPlanComingYnAtom,
  myPlanCreatedYearAtom,
  myPlanCurrentPageAtom,
  myPlanSearchTypeAtom,
} from 'src/atoms/myPlan';

import FailDataPage from './FailDataPage';

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
  totalElements: number;
}

const MyPlanPage = () => {
  const [searchType, setSearchType] = useRecoilState(myPlanSearchTypeAtom);
  const [comingYn, setComingYn] = useRecoilState(myPlanComingYnAtom);
  const [createdYear, setCreatedYear] = useRecoilState(myPlanCreatedYearAtom); // 작성연도
  const [currentPage, setCurrentPage] = useRecoilState(myPlanCurrentPageAtom); // 현재 페이지 번호

  const [currentPost, setCurrentPost] = useState<Plan[]>([]); // 게시판 목록에 보여줄 게시글
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

  useEffect(() => {
    if (!isLoading) {
      setCurrentPost(myPlanData?.data.contents);
    }
  }, [myPlanData]);

  // 페이지네이션
  let totalElements = myPlanData?.data.totalElements;
  if (totalElements === 0) totalElements = 1;

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  // 작성연도 필터
  const currentYear = new Date().getFullYear();
  const endYear = 2023;
  const createdYearList = [6];

  for (let year = currentYear; year >= endYear; year -= 1) {
    createdYearList.push(year);
  }

  const handleClick = (year: number) => {
    if (year === createdYear) setCreatedYear(null);
    else setCreatedYear(year);
  };

  useEffect(() => {
    setCreatedYear(null);
    setSearchType('T');
    setComingYn(null);
    setCurrentPage(1);
  }, []);

  const searchTypeObj = {
    제목: 'T',
    지역: 'R',
  };

  const comingYnObj = {
    전체: null,
    '다가오는 여행': 'Y',
    '지난 여행': 'N',
  };

  return (
    <div>
      <MyPageTitle>내 여행계획</MyPageTitle>
      <SearchBox>
        <SearchTypeDrop>
          <DropDown filter={searchTypeObj} setParams={setSearchType} />
        </SearchTypeDrop>
        <MyPlanSearch placeHolder="제목 또는 지역으로 검색해보세요." setKeyword={setKeyword} />
        <ComingYnDrop>
          <DropDown filter={comingYnObj} setParams={setComingYn} />
        </ComingYnDrop>
      </SearchBox>
      <FilterBox>
        {createdYearList?.map((year) => (
          <Button
            key={year}
            onClick={() => handleClick(year)}
            $isClicked={createdYear === year ?? false}
          >
            {year === 6 ? '최근 6개월' : year}
          </Button>
        ))}
      </FilterBox>
      <PlanListCategory />
      {isError && <FailDataPage />}
      <ListContainer>
        {currentPost?.length === 0 && <EmptyBox>계획 목록이 없습니다.</EmptyBox>}
        {currentPost?.map((data, idx) => {
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
      <Paging
        currentPage={currentPage}
        totalElements={totalElements}
        setCurrentPage={handlePageChange}
      />
    </div>
  );
};

export default MyPlanPage;

const ListContainer = styled.div`
  max-height: 48vh;
  min-height: 48vh;
  overflow-y: auto;

  ${media.mobile`
    width: 100%;
    min-height: 28vh;
  `}
`;

const SearchBox = styled.div`
  display: flex;
  align-items: center;

  margin-bottom: 15px;

  ${media.mobile`
    margin-bottom: 20px;
  `}
`;

const FilterBox = styled.div`
  display: flex;

  ${media.mobile`
    margin-bottom: 30px;
  `}
`;

const Button = styled.button<{ $isClicked: boolean }>`
  ${FilterButtonStyle};

  height: 25px;
  margin: 0 10px 10px 0;

  ${media.mobile`
    height: 35px;
    font-size: 15px;
  `}
`;

const SearchTypeDrop = styled.div`
  width: 90px;

  ${media.mobile`
    width: 170px;
  `}
`;

const ComingYnDrop = styled.div`
  width: 150px;

  ${media.mobile`
    width: 250px;
  `}
`;
