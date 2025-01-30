#!/bin/ash
#above is shebang for alpine

#JAVA_OPTS="-Dspring.profiles.active=prod"
echo "JVM options: $JAVA_OPTS"

exec java $JAVA_OPTS -jar /app/app.jar
