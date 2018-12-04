FROM maven:3.6-jdk-8-alpine as builder
RUN mkdir /code
WORKDIR /code
COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline
COPY src/ ./src/
RUN mvn -B -e -o -T 1C clean install

FROM openjdk:8-jre-alpine
RUN apk add --no-cache bash
RUN mkdir /app
COPY --from=builder /code/target /app
WORKDIR /app
