import styled from 'styled-components';
import React, { useState, useEffect } from 'react';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';

import { useRegions } from 'src/hooks/api/useRegions';
import { Regions } from 'src/@types/api/regions';
import regionIdAtom from 'src/atoms/regionIdAtom';
import regionsAtom from 'src/atoms/regionsAtom';
import modalStateAtom from 'src/atoms/modalStateAtom';
import createScheduleAtom from 'src/atoms/createScheduleAtom';

import Loading from 'src/components/common/Loading';
import RegionModal from 'src/components/RegionModal';
import RegionItem from './RegionItem';
import { ReactComponent as FindIcon } from '../../../assets/svgs/findIcon.svg';
import DefaultImg from '../../../assets/images/defaultImg.png';

interface RegionListData {
  data: { data: Regions[] };
  isLoading: boolean;
  isError: boolean;
}

const RegionList = () => {
  const { data: regionsApi, isLoading, isError }: RegionListData = useRegions();
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [displayRegions, setDisplayRegions] = useState<Regions[]>([]);

  const isConsonantOrVowel = (text: string) => {
    const pattern = /[ㄱ-ㅎㅏ-ㅣ]/;
    return pattern.test(text);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value;
    if (inputValue === '') {
      setSearchTerm('');
    } else {
      setSearchTerm(inputValue);
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Backspace' && searchTerm.length === 1) {
      setDisplayRegions(regionsApi?.data || []);
    } else if (e.key === 'Backspace' && searchTerm.length > 0) {
      setSearchTerm(searchTerm.slice(0, searchTerm.length - 1));
    }
  };

  const setRegions = useSetRecoilState(regionsAtom);
  const setCreateSchedule = useSetRecoilState(createScheduleAtom);
  const [isModal, setModal] = useRecoilState(modalStateAtom);
  const currentId = useRecoilValue(regionIdAtom);

  useEffect(() => {
    if (regionsApi?.data) {
      setRegions(regionsApi.data);
      setCreateSchedule(true);
    }
  }, [regionsApi]);

  useEffect(() => {
    if (regionsApi?.data) {
      const filteredRegions = regionsApi?.data.filter((data) => data.name.includes(searchTerm));
      setDisplayRegions(!isConsonantOrVowel(searchTerm) ? filteredRegions : regionsApi.data);
    }
  }, [regionsApi, searchTerm]);

  useEffect(() => {
    setModal(false);
    document.body.style.overflowY = 'auto';
  }, []);

  const onClose = () => {
    setModal(false);
    document.body.style.overflowY = 'auto';
  };

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
            onKeyDown={handleKeyDown}
            placeholder="여행지를 검색해보세요!"
          />
        </SearchBar>
      </SearchContainer>
      {isLoading && <Loading type="MEDIUM" />}
      {isError && <CannotLoading>데이터를 불러오는 데 실패했습니다.</CannotLoading>}
      {!isLoading && displayRegions.length === 0 && !isError && (
        <NoSearchResults>
          검색 결과가 없습니다.
          <br /> 전체 지역을 확인해보세요.
        </NoSearchResults>
      )}
      <ListContainer>
        <List>
          {Array.isArray(displayRegions) &&
            displayRegions.map((data) => (
              <RegionItem
                key={data.id}
                id={data.id}
                src={data.imageThumbnail || DefaultImg}
                name={data.name}
              />
            ))}
        </List>
      </ListContainer>
      {isModal && <RegionModal id={currentId} onClose={onClose} />}
    </div>
  );
};

export default RegionList;

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
  display: flex;

  width: 550px;
  height: 57px;
  margin: 0 auto 80px auto;

  box-shadow: 0px 3px 7px -1px darkGray;
  border-radius: 30px;

  align-items: center;
  justify-content: center;
`;

const IconBox = styled.div`
  svg {
    width: 20px;
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

const ListContainer = styled.div`
  height: 450px;
`;

const List = styled.div`
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

const NoSearchResults = styled.div`
  height: 10px;

  font-family: 'Pretendard-SemiBold';
  font-size: 20px;
  color: #676767;
  line-height: 29px;
  text-align: center;
`;
