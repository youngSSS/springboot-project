#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=ys-springboot-webservice

echo "> Copy Build Files"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Check Application pid On Running"

CURRENT_PID=$(pgrep -fl java | awk '{print $1}')

echo "Application pid On Running: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> No Application Is On Running. Application Was Not Terminated"
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploy New Application"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> Add Executive Authority to $JAR_NAME"

chmod +x $JAR_NAME

echo "Run $JAR_NAME"

nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
  -Dspring.profiles.active=real \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &