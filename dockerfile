FROM alpine:latest
RUN apk --no-cache add openjdk21-jre
#FROM openjdk:17
WORKDIR /app
COPY entrypoint.sh entrypoint.sh
COPY target/reef-forge-backend-0.0.1-SNAPSHOT.jar app.jar
RUN chmod +x /app/entrypoint.sh
EXPOSE 9080
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["/app/entrypoint.sh"]