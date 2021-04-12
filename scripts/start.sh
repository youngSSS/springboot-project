#!/usr/bin/env bash

# start.sh 역할
# 배포할 신규 버전 Spring Boot를 stop.sh로 종료한 profile로 실행

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=ys-springboot-webservice

echo "> Copy Build Files"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Deploy New Application"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR_NAME: $JAR_NAME"

echo "> Add Executive Authority to $JAR_NAME"

chmod +x $JAR_NAME

echo "> Run $JAR_NAME"

IDLE_PROFILE=$(find_idle_profile)

echo "> Run $JAR_NAME as profile=$IDLE_PROFILE"
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &