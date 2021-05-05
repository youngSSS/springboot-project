#!/usr/bin/env bash

# switch.sh 역할
# NGINX가 바라보는 Spring Boot를 최신 버전으로 변경

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> Port For Switching: $IDLE_PORT"
    echo "> Switch Port"
    # NGINX가 변경할 proxy 주소 생성
    # "set \$service_url http://127.0.0.1:${IDLE_PORT};"
    # 앞에서 넘겨준 문장을 service-url.inc에 덮어쓴다
    # sudo tee /etc/nginx/conf.d/service_url.inc
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service_url.inc

    echo "> Reload NGINX"
    sudo service nginx reload
}