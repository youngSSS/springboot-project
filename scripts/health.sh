#!/usr/bin/env bash

# helth.sh 역할
# start.sh로 실행시킨 프로젝트가 정상적으로 실행됐는지 확인

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("real" 문자열이 있는지 검증)
    echo "> Health Check Success"
    switch_proxy
    break
  else
    echo "> The Response From Health Check Is Unknown Or Not Running"
    echo "> Health Check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health Check Fail"
    echo "> Stop Deploying Without Connecting To NGINX"
    exit 1
  fi

  echo "> Fail To Connect Health Check. Retry..."
  sleep 10
done