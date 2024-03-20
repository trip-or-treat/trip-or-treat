// 개발완료시 배포 도메인으로 변경
const REDIRECT_URI = 'https://localhost:3000/login';
const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${REDIRECT_URI}`;

export default KAKAO_AUTH_URL;
