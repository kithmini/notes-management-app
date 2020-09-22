### Stage 1
# Base image Java 11 open jdk
FROM openjdk:8-jdk-alpine as build
# Working directory inside the image
WORKDIR /app
# Copy maven executables
COPY mvnw .
COPY .mvn .mvn
# Copy pom.xml
COPY pom.xml .
# Build all the dependencies preparing to go offline.
# Caches depndencies unless pom.xml has changed
RUN ./mvnw dependency:go-offline -B
# Copy project source
COPY src src
# Package the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

### Stage 2
# JRE image runtime to run the app
FROM openjdk:8-jre-alpine

ARG DEPENDENCY=/app/target/dependency
# Copy dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.bkitsen.notesapp.NotesAppApplication"]