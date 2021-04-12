#!/usr/bin/env bash

# profile.sh 역할
# stop.sh, start.sh, health.sh, switch.sh에서 공용으로 사용할 profile과 포트 테크 로직 수행

# 쉬고 있는 profile 찾기: real1이 사용 중이면 real2가 선택되며, 역 또한 성립
function find_idle_profile() {
  # 현재 NGINX가 바라보고 있는 Spring Boot가 정상 수행 중인지 확인 (HttpStatus로 응답을 받는다)
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  # HttpStatus가 정상이면 200, 오류가 발생한다면 400 ~ 503 사이로 발생하니 400 이상은 모두 예외로 보고 real2를 현재 profile로 사용
  if [ ${RESPONSE_CODE} -ge 400 ]
  then
    CURRENT_PROFILE=real2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port() {
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}