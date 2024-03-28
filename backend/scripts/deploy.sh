#!/usr/bin/env bash

echo "#############################"

REPOSITORY=/home/ubuntu/triportreat
cd $REPOSITORY

JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

# 운영서버, 개발서버 pid
MAIN_PID=$(pgrep -f production)
DEV_PID=$(pgrep -f develop)

# 로그 파일 경로
MAIN_LOG_OUT="/home/ubuntu/logs/main.out"
DEV_LOG_OUT="/home/ubuntu/logs/dev.out"
MAIN_LOG_ERR="/home/ubuntu/logs/main.err"
DEV_LOG_ERR="/home/ubuntu/logs/dev.err"


if [[ "$JAR_NAME" == *"main"* ]]; then
  echo "운영서버를 배포합니다..."

  if [ -n $MAIN_PID ]; then
    echo "운영서버 실행중...종료합니다"
    kill -15 $MAIN_PID
    sleep 5
  else 
    echo "운영서버가 실행중이 아닙니다"
  fi

  echo "운영서버를 시작합니다..."
  nohup java -jar -Dspring.profiles.active=production $JAR_PATH >$MAIN_LOG_OUT 2>$MAIN_LOG_ERR < /dev/null &

else
  echo "개발서버를 배포합니다..."

  if [ -n $DEV_PID ]; then
    echo "개발서버 실행중...종료합니다"
    kill -15 $DEV_PID
    sleep 5
  else 
    echo "개발서버가 실행중이 아닙니다..."
  fi

  echo "개발서버를 시작합니다..."
  nohup java -jar -Dspring.profiles.active=develop $JAR_PATH >$DEV_LOG_OUT 2>$DEV_LOG_ERR < /dev/null &

fi