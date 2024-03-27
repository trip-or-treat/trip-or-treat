import 'styled-components';
import { Theme } from './theme';

interface Colors {
  mainColor: string;
  commonNavBgColor: string;
  darkGrey: string;
  lightGrey: string;
  blackFont: string;
  lightGreyFont: string;
  whiteFont: string;
  hoverColor: string;
  yellow: string;
}

interface Width {
  leftNavWidth: string;
}

interface Height {
  topNavHeight: string;
}

interface Size {
  regionItemSize: string;
}

declare module 'styled-components' {
  export interface DefaultTheme extends Theme {
    colors: Colors;
    width: Width;
    height: Height;
  }
}
