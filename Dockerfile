FROM docker.io/maven:3.9-eclipse-temurin-21-noble AS builder
RUN apt-get -y update && apt-get -y upgrade && apt-get -y install wget

ARG REPO_ACCESS_TOKEN
ARG GIT_USER

SHELL ["/bin/bash", "-c"]

WORKDIR /licenseengine
COPY . /licenseengine

RUN git clone "https://$GIT_USER:$REPO_ACCESS_TOKEN@github.com/ffritze/Spdx-Java-Library-Rdf.git"

RUN cd Spdx-Java-Library-Rdf && mvn clean install -DskipTests -Dmaven.repo.local=/licenseengine/.m2

RUN cd /licenseengine

RUN cd /licenseengine/src/test/resources && wget https://github.com/RePlay-DH/replay-dh-client/releases/download/v1.3.0/replay-dh-client-1.3.0.zip

RUN cd /licenseengine

RUN mvn clean package -Dmaven.repo.local=/licenseengine/.m2

RUN rm -fr /licenseengine/.m2
    
FROM dhi.io/maven:3-jdk21-debian13-dev

WORKDIR /licenseengine
COPY --from=builder /licenseengine /licenseengine

ENTRYPOINT  ["java","-jar","target/licenseengine-0.0.3.jar"]