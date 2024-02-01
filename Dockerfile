FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY . /app

RUN ./gradlew bootJar

RUN cp ./build/libs/harmonika-0.0.1-SNAPSHOT.jar /harmonika-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/harmonika-0.0.1-SNAPSHOT.jar"]