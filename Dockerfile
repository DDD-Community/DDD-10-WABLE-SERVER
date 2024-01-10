FROM eclipse-temurin:17-jdk-focal

# TODO: jib 추가
WORKDIR /app

COPY . /app

RUN ./gradlew bootJar

RUN cp /app/build/libs/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
