import { useState } from 'react';
import styled from 'styled-components';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import createScheduleAtom from 'src/atoms/createScheduleAtom';
import regionsAtom from 'src/atoms/regionsAtom';
import RegionModal from '../RegionModal';
import RegionCard from '../common/RegionCard';

const SearchRegionList = () => {
  const setCreateSchedule = useSetRecoilState(createScheduleAtom);
  const [isModal, setModal] = useState(false);
  const [currentId, setCurrentId] = useState(0);

  setCreateSchedule(false);

  const onClose = () => {
    setModal(false);
  };

  const regions = useRecoilValue(regionsAtom);
  return (
    <Wrapper>
      {regions.map((item) => (
        <RegionCard
          key={item.id}
          item={item}
          type="ADD_BUTTON"
          onOpen={setModal}
          setCurrentId={setCurrentId}
        />
      ))}
      {isModal && <RegionModal id={currentId} src={regions[0].imageThumbnail} onClose={onClose} />}
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
