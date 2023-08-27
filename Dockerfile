# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-oracle

WORKDIR /app
#for remote debugging
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
COPY build/libs/tuum-account-*-SNAPSHOT.jar app.jar
EXPOSE 8080 5005

CMD ["java", "-jar", "app.jar"]