FROM alpine:latest
RUN apk --no-cache add openjdk21-jre
#FROM openjdk:17
WORKDIR /app
COPY reef-forge-backend/entrypoint.sh entrypoint.sh
COPY reef-forge-backend/target/reef-forge-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9080
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["/app/entrypoint.sh"]