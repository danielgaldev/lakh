FROM maven:3.3.9-jdk-8-alpine
RUN mkdir /code
WORKDIR /code
ADD . /code/
