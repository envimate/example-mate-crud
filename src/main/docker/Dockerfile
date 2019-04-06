FROM openjdk:12

RUN mkdir -p /opt/application
WORKDIR /opt/application

ARG VERSION
ENV VERSION ${VERSION:-0.0.2}

ADD target/example-mate-crud-${VERSION}-jar-with-dependencies.jar /opt/application/example-mate-crud-${VERSION}.jar

EXPOSE 1337

ENTRYPOINT exec java -jar /opt/application/example-mate-crud-${VERSION}.jar
