import styled from 'styled-components';

interface Props {
  type: 'SMALL' | 'MEDIUM' | 'LARGE';
}

const Loading = ({ type }: Props) => {
  return (
    <>
      {type === 'SMALL' && <SmallLoading />}
      {type === 'MEDIUM' && <MediumLoading />}
      {type === 'LARGE' && <LargeLoading />}
    </>
  );
};

export default Loading;

const Default = styled.div`
  margin: 10px auto;
  border-radius: 50%;

  animation: load 1.5s linear infinite;

  @keyframes load {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }
`;

const SmallLoading = styled(Default)`
  width: 20px;
  height: 20px;
  border: 5px solid #dcdcdc;
  border-bottom: 5px solid ${(props) => props.theme.colors.mainColor};
`;

const MediumLoading = styled(Default)`
  width: 43px;
  height: 43px;
  border: 7px solid #dcdcdc;
  border-bottom: 7px solid ${(props) => props.theme.colors.mainColor};
`;

const LargeLoading = styled(Default)`
  width: 70px;
  height: 70px;
  border: 9px solid #dcdcdc;
  border-bottom: 9px solid ${(props) => props.theme.colors.mainColor};
`;
