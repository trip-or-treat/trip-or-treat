import { createGlobalStyle } from 'styled-components';

import PretendardBold2 from '../assets/fonts/Pretendard-Bold.woff2';
import PretendardBold from '../assets/fonts/Pretendard-Bold.woff';

import PretendardSemiBold2 from '../assets/fonts/Pretendard-SemiBold.woff2';
import PretendardSemiBold from '../assets/fonts/Pretendard-SemiBold.woff';

import PretendardRegular2 from '../assets/fonts/Pretendard-Regular.woff2';
import PretendardRegular from '../assets/fonts/Pretendard-Regular.woff';

import PretendardMedium2 from '../assets/fonts/Pretendard-Medium.woff2';
import PretendardMedium from '../assets/fonts/Pretendard-Medium.woff';

import PretendardLight2 from '../assets/fonts/Pretendard-Light.woff2';
import PretendardLight from '../assets/fonts/Pretendard-Light.woff';

import PretendardThin2 from '../assets/fonts/Pretendard-Thin.woff2';
import PretendardThin from '../assets/fonts/Pretendard-Thin.woff';

const GlobalStyle = createGlobalStyle`
  html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed, 
figure, figcaption, footer, header, hgroup, 
menu, nav, output, ruby, section, summary,
time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
	font: inherit;
	vertical-align: baseline;
}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, 
footer, header, hgroup, menu, nav, section {
	display: block;
}
body {
	line-height: 1;
}
ol, ul {
	list-style: none;
}
blockquote, q {
	quotes: none;
}
blockquote:before, blockquote:after,
q:before, q:after {
	content: '';
	content: none;
}
table {
	border-collapse: collapse;
	border-spacing: 0;
}
@font-face {
	font-family: 'Pretendard-Bold';
	src: url(${PretendardBold2}) format('woff2'),
	url(${PretendardBold}) format('woff');
}
@font-face {
	font-family: 'Pretendard-SemiBold';
	src: url(${PretendardSemiBold2}) format('woff2'),
	url(${PretendardSemiBold}) format('woff');
}
@font-face {
	font-family: 'Pretendard-Regular';
	src: url(${PretendardRegular2}) format('woff2'),
	url(${PretendardRegular}) format('woff');
}
@font-face {
	font-family: 'Pretendard-Medium';
	src: url(${PretendardMedium2}) format('woff2'),
	url(${PretendardMedium}) format('woff');
}
@font-face {
	font-family: 'Pretendard-Light';
	src: url(${PretendardLight2}) format('woff2'),
	url(${PretendardLight}) format('woff');
}
@font-face {
	font-family: 'Pretendard-Thin';
	src: url(${PretendardThin2}) format('woff2'),
	url(${PretendardThin}) format('woff');
}


.react-datepicker {
    position: relative;
	
    border: none;

	font-family: Pretendard-Light;
    font-size: 14px;
}

.react-datepicker__header {
    position: relative;
    padding: 8px 0;
	margin: 5px;
    border-bottom: none;

	background-color: ${(props) => props.theme.colors.whiteFont};
    font-size: 20px;
    text-align: center;
}

.react-datepicker__current-month {
    margin-bottom: 30px;

    font-family: Pretendard-SemiBold;
    font-size: 20px;
}
.react-datepicker__day-name {
    width: 45px;

	margin-left: 3px;

	font-size: 16px;
}

.react-datepicker__day {
	display: inline-block;
    width: 45px;
    margin: 3px;
	
    line-height: 35px;
    text-align: center;
}

.react-datepicker__day:hover {
	background-color:${(props) => props.theme.colors.mainColor};
	color: ${(props) => props.theme.colors.whiteFont}
}

.react-datepicker__day--sat {
    color: blue;
}

.react-datepicker__day--sun {
    color: red;
}

.react-datepicker__day--in-selecting-range:not(.react-datepicker__day--in-range, .react-datepicker__month-text--in-range, .react-datepicker__quarter-text--in-range, .react-datepicker__year-text--in-range) {
	background-color:${(props) => props.theme.colors.lightGrey};
	color: ${(props) => props.theme.colors.whiteFont}
}

.react-datepicker__day--keyboard-selected, .react-datepicker__month-text--keyboard-selected, .react-datepicker__quarter-text--keyboard-selected, .react-datepicker__year-text--keyboard-selected {
	background-color: unset;
}

.react-datepicker__day--in-range {
	background-color:${(props) => props.theme.colors.mainColor};
	color: ${(props) => props.theme.colors.whiteFont}
}

.pagination {
    display: flex;
    justify-content: center;

    margin-top: 30px;
}
  
  ul.pagination li {
    /* display: inline-block; */
    width: 30px;
    height: 30px;

    display: flex;
    justify-content: center;
    align-items: center;

    font-size: 15px;
  }

  /* ul.pagination li:first-child{
    border-radius: 5px 0 0 5px;
  }

  ul.pagination li:last-child{
    border-radius: 0 5px 5px 0;
  } */
  
  ul.pagination li a {
    text-decoration: none;
    color: ${(props) => props.theme.colors.blackFont};
  }

  ul.pagination li a:hover {
    font-weight: bold;
  }
  
  ul.pagination li.active a {
    color: ${(props) => props.theme.colors.whiteFont};
  }

  ul.pagination li.active {
    background-color: ${(props) => props.theme.colors.mainColor};
  }

  
  
  /* .page-selection {
    width: 48px;
    height: 30px;
    color: black;
  } */

`;

export default GlobalStyle;
