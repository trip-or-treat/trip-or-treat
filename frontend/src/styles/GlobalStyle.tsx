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

`;

export default GlobalStyle;
