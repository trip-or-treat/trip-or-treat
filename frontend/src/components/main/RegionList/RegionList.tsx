import styled from 'styled-components';
import React, { useState } from 'react';

import { useRegions } from 'src/hooks/api/useRegions';
import Loading from 'src/components/common/Loading';
import RegionItem from './RegionItem';
import { ReactComponent as FindIcon } from '../../../assets/svgs/findIcon.svg';

const RegionList = () => {
  const { data: regionsData, isLoading, isError } = useRegions();
  const [searchTerm, setSearchTerm] = useState<string>('');

  // 검색어에 해당하는 지역 리스트를 필터링하는 함수
  const filteredRegions = regionsData?.filter((region) => region.name.includes(searchTerm));

  // 자음 또는 모음인지 확인하는 함수
  const isConsonantOrVowel = (text: string) => {
    const pattern = /[ㄱ-ㅎㅏ-ㅣ]/;
    return pattern.test(text);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  // 검색어가 없거나 결과가 없을 때는 전체 데이터 리스트를 보여줌
  const displayRegions =
    searchTerm && !isConsonantOrVowel(searchTerm) ? filteredRegions || [] : regionsData || [];

  return (
    <div>
      <SearchContainer>
        <Text>어디로 여행을 떠나시나요?</Text>
        <SearchBar>
          <IconBox>
            <FindIcon />
          </IconBox>
          <Input
            type="text"
            value={searchTerm}
            onChange={handleChange}
            placeholder="여행지를 검색해보세요!"
          />
        </SearchBar>
      </SearchContainer>
      {isLoading && <Loading type="MEDIUM" />}
      {isError && <CannotLoading>데이터를 불러오는 데 실패했습니다.</CannotLoading>}
      <ListContainer>
        {!isLoading && displayRegions.length > 0 ? (
          displayRegions.map((region) => (
            <RegionItem key={region.id} src={region.imageThumbnail} name={region.name} />
          ))
        ) : (
          <NoSearchResults>
            검색 결과가 없습니다.
            <br /> 전체 지역을 확인해보세요.
          </NoSearchResults>
        )}
      </ListContainer>
    </div>
  );
};

export default RegionList;

// 지역 리스트
const ListContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(4, ${(props) => props.theme.size.regionItemSize});
  column-gap: 55px;
  row-gap: 155px;

  justify-content: center;
`;

const CannotLoading = styled.div`
  height: 70px;
  margin-top: 50px;

  font-family: 'Pretendard-SemiBold';
  font-size: 20px;
  color: ${(props) => props.theme.colors.blackFont};
  text-align: center;
`;

const SearchContainer = styled.div`
  text-align: center;
`;

const Text = styled.div`
  margin-bottom: 40px;

  font-family: 'Pretendard-Bold';
  font-size: 30px;
  color: ${(props) => props.theme.colors.blackFont};
`;

const SearchBar = styled.div`
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 550px;
  height: 57px;

  margin-bottom: 80px;
  border-radius: 30px;
  box-shadow: 0px 3px 7px -1px darkGray;
`;

const IconBox = styled.div`
  svg {
    width: 20px;
    float: left;
  }
`;

const Input = styled.input`
  border: none;

  font-family: 'Pretendard-Regular';
  font-size: 19px;
  color: ${(props) => props.theme.colors.blackFont};
  text-align: center;

  outline: none;
`;

const NoSearchResults = styled.div`
  height: 300px;
  line-height: 29px;
  font-family: 'Pretendard-SemiBold';
  font-size: 20px;
  color: ${(props) => props.theme.colors.blackFont};
  text-align: center;
`;
