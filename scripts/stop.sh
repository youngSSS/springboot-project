#!/usr/bin/env bash

# stop.sh 역할
# 기존 NGINX에 연결되어 있진 않지만, 실행 중이던 Spring Boot 종료

# profile.sh의 경로를 찾기 위해, 현재 stop.sh가 속해 있는 경로를 찾습니다.
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
# 일종의 import 구문입니다. 해당 코드를 통해 profile.sh의 함수를 사용할 수 있게 됩니다.
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "> Application pid Running On $IDLE_PORT"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> No Application Is On Running. Application Was Not Terminated"
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi