#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/triportreat
cd $REPOSITORY

JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

# 실행되고 있는 운영서버 PID
MAIN_PID=$(pgrep -f production)
# 실행되고 있는 개발 서버 PID
DEV_PID=$(pgrep -f develop)

# 정상 실행 로그 파일
MAIN_LOG_OUT="/home/ubuntu/logs/main.out"
DEV_LOG_OUT="/home/ubuntu/logs/dev.out"
# 에러 로그 파일
MAIN_LOG_ERR="/home/ubuntu/logs/main.err"
DEV_LOG_ERR="/home/ubuntu/logs/dev.err"


# 혹시 개발서버가 실행중일 경우 종료
if [ -n $DEV_PID ]; 
then
	echo "개발서버 실행중...종료합니다"
	kill -15 $DEV_PID
	sleep 5
else 
	echo "개발서버가 실행중이 아닙니다"
fi


# 메인브랜치로 푸시될 경우
# TODO main브랜치로 수정
if [ "$GITHUB_REF" = "refs/heads/CHORE/TOT-52" ]; then
	echo "메인브랜치가 푸시되었습니다. 메인 프로파일로 실행합니다."

  # 이전에 실행된 프로세스의 PID를 찾아 종료
  if [ -n $MAIN_PID ]; 
	then
    echo "메인 프로세스를 종료합니다..."
    kill -15 $MAIN_PID
    sleep 5
  else
    echo "메인 프로세스가 실행중이 아닙니다..."
  fi
	
	echo "메인 프로세스를 실행합니다..."
	nohup java -jar -Dspring.profiles.active=production $JAR_PATH >$MAIN_LOG_OUT 2>$MAIN_LOG_ERR < /dev/null &

else
  # 개발 브랜치로 푸시될 경우
  echo "개발브랜치가 푸시되었습니다. 개발 프로파일로 잠시 실행합니다."
  
  # 프로세스 시작
  echo "develop 프로파일로 실행합니다..."
  nohup java -jar -Dspring.profiles.active=develop $JAR_PATH >$DEV_LOG_OUT 2>$DEV_LOG_ERR < /dev/null &
  
  # 프로세스 시작 후 바로 종료
  echo "개발 프로세스를 종료합니다..."
	kill -15 $DEV_PID
	sleep 5
fi