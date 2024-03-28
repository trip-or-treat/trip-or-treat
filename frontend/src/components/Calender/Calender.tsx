import { useState } from 'react';
import DatePicker from 'react-datepicker';
import { useSetRecoilState } from 'recoil';
import { ko } from 'date-fns/locale';

import createScheduleAtom from 'src/atoms/createScheduleAtom';
import totalPlanAtom from 'src/atoms/totalPlanAtom';

import 'react-datepicker/dist/react-datepicker.css';

const Calendar = () => {
  const defaultMaxDate = new Date();
  defaultMaxDate.setFullYear(defaultMaxDate.getFullYear() + 1);

  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [maxDate, setMaxDate] = useState<Date>(defaultMaxDate);
  const setPlan = useSetRecoilState(totalPlanAtom);
  const setActivate = useSetRecoilState(createScheduleAtom);

  const isAfterYesterday = (date: Date): boolean => {
    const yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);

    return date > yesterday;
  };

  const isSaturday = (date: Date): boolean => date.getDay() === 6;
  const isSunday = (date: Date): boolean => date.getDay() === 0;

  const formatDate = (date: Date): string => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const setDateData = (start: Date, end: Date) => {
    const data = [];
    const currentDate = new Date(start);
    let day = 1;

    while (currentDate <= end) {
      data.push({
        day,
        date: formatDate(currentDate),
        items: [],
      });
      currentDate.setDate(currentDate.getDate() + 1);
      day += 1;
    }

    return data;
  };

  const onChange = (dates: [Date, Date]) => {
    const [start, end] = dates;

    if (startDate !== start) {
      setActivate(true);
      setStartDate(start);

      const sevenDaysLater = new Date(start.getTime());
      sevenDaysLater.setDate(start.getDate() + 6);
      setMaxDate(sevenDaysLater);
    }
    if (endDate !== end) {
      setEndDate(end);
    }

    if (start && end) {
      setMaxDate(defaultMaxDate);
      setPlan(setDateData(start, end));
      setActivate(false);
    }
  };

  return (
    <DatePicker
      locale={ko}
      inline
      selectsRange
      monthsShown={2}
      minDate={new Date()}
      maxDate={maxDate}
      startDate={startDate}
      endDate={endDate}
      onChange={onChange}
      dayClassName={(date: Date) => {
        if (isSaturday(date) && isAfterYesterday(date) && date <= maxDate)
          return 'react-datepicker__day--sat';
        if (isSunday(date) && isAfterYesterday(date) && date <= maxDate)
          return 'react-datepicker__day--sun';

        return null;
      }}
    />
  );
};

export default Calendar;
