import styled from 'styled-components';
import { useEffect } from 'react';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';

import createScheduleAtom from 'src/atoms/createScheduleAtom';
import modalStateAtom from 'src/atoms/modalStateAtom';
import regionIdAtom from 'src/atoms/regionIdAtom';
import regionsAtom from 'src/atoms/regionsAtom';

import RegionModal from '../RegionModal';
import RegionCard from '../common/RegionCard';

interface Props {
  keyword: string;
}

const SearchRegionList = ({ keyword }: Props) => {
  const regions = useRecoilValue(regionsAtom);
  const filtered = regions.filter((data) => data.name === keyword);
  const setCreateSchedule = useSetRecoilState(createScheduleAtom);
  const [isModal, setModal] = useRecoilState(modalStateAtom);
  const currentId = useRecoilValue(regionIdAtom);

  useEffect(() => {
    setCreateSchedule(false);
  }, []);

  const onClose = () => {
    setModal(false);
    document.body.style.overflowY = 'auto';
  };

  if (filtered.length === 0 && keyword !== '') {
    alert(`${keyword}에 대한 일치하는 정보가 없습니다`);
  }

  return (
    <Wrapper>
      {(filtered.length === 0 ? regions : filtered).map((item) => (
        <RegionCard key={item.id} item={item} type="ADD_BUTTON" />
      ))}
      {isModal && <RegionModal id={currentId} onClose={onClose} />}
    </Wrapper>
  );
};

export default SearchRegionList;

const Wrapper = styled.div`
  overflow-y: auto;

  width: 100%;
  height: 288px;
  padding: 0px 20px;
  margin-bottom: 5px;

  box-sizing: border-box;
`;
