import Pagination from 'react-js-pagination';

interface Props {
  currentPage: number;
  totalElements: number;
  setCurrentPage(arg: number): void;
}

const Paging = ({ currentPage, totalElements, setCurrentPage }: Props) => {
  return (
    <Pagination
      activePage={currentPage} // 현재페이지
      itemsCountPerPage={10} // 한 페이지 당 보여줄 데이터 수
      totalItemsCount={totalElements} // 총 데이터 수
      pageRangeDisplayed={5} // 페이지네이터 몇 개 보여줄지
      prevPageText="<" // 이전 텍스트
      nextPageText=">" // 다음 텍스트
      onChange={setCurrentPage} // 페이지가 바뀔때 실행되는 함수
    />
  );
};

export default Paging;
