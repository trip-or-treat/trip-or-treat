import 'styled-components';
import { Theme } from './theme';

interface Colors {
  mainColor: string;
  darkGrey: string;
  lightGrey: string;
  blackFont: string;
  lightGreyFont: string;
  whiteFont: string;
}

declare module 'styled-components' {
  export interface DefaultTheme extends Theme {
    colors: Colors;
  }
}
