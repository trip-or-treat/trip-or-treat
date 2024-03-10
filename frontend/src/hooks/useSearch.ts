import { useEffect } from 'react';
import { Regions } from 'src/@types/api/regions';

interface Props {
  regionsApi: {
    data: Regions[];
  };
  searchTerm: string;
  setDisplayRegions: (regions: Regions[]) => void;
}

export const useSearch = ({ regionsApi, searchTerm, setDisplayRegions }: Props) => {
  useEffect(() => {
    if (regionsApi?.data) {
      const searchTermTrimmed = searchTerm.trim();
      const filteredRegions = regionsApi?.data.filter((region: Regions) => {
        const regionNameTrimmed = region.name.replace(/\s+/g, '');

        const searchTermConverted = convertToKoreanJamo(searchTermTrimmed);
        const regionNameConverted = convertToKoreanJamo(regionNameTrimmed);

        return regionNameConverted.includes(searchTermConverted);
      });
      setDisplayRegions(filteredRegions);
    }
  }, [regionsApi?.data, searchTerm]);
};

function convertToKoreanJamo(keyword: string) {
  return keyword
    .split('')
    .map((character) => {
      const unicode = character.charCodeAt(0);
      if (unicode >= 0xac00 && unicode <= 0xd7a3) {
        const index = unicode - 0xac00;
        const jongIndex = index % 28;
        const jungIndex = Math.floor(((index - jongIndex) / 28) % 21);
        const choIndex = Math.floor((index - jongIndex) / 28 / 21);
        return cho[choIndex] + jung[jungIndex] + (jongIndex > 0 ? jong[jongIndex] : '');
      }
      return character;
    })
    .join('');
}

const cho = [
  'ㄱ',
  'ㄲ',
  'ㄴ',
  'ㄷ',
  'ㄸ',
  'ㄹ',
  'ㅁ',
  'ㅂ',
  'ㅃ',
  'ㅅ',
  'ㅆ',
  'ㅇ',
  'ㅈ',
  'ㅉ',
  'ㅊ',
  'ㅋ',
  'ㅌ',
  'ㅍ',
  'ㅎ',
];
const jung = [
  'ㅏ',
  'ㅐ',
  'ㅑ',
  'ㅒ',
  'ㅓ',
  'ㅔ',
  'ㅕ',
  'ㅖ',
  'ㅗ',
  'ㅘ',
  'ㅙ',
  'ㅚ',
  'ㅛ',
  'ㅜ',
  'ㅝ',
  'ㅞ',
  'ㅟ',
  'ㅠ',
  'ㅡ',
  'ㅢ',
  'ㅣ',
];
const jong = [
  '',
  'ㄱ',
  'ㄲ',
  'ㄳ',
  'ㄴ',
  'ㄵ',
  'ㄶ',
  'ㄷ',
  'ㄹ',
  'ㄺ',
  'ㄻ',
  'ㄼ',
  'ㄽ',
  'ㄾ',
  'ㄿ',
  'ㅀ',
  'ㅁ',
  'ㅂ',
  'ㅄ',
  'ㅅ',
  'ㅆ',
  'ㅇ',
  'ㅈ',
  'ㅊ',
  'ㅋ',
  'ㅌ',
  'ㅍ',
  'ㅎ',
];
