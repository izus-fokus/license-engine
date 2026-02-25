FROM docker.io/maven:3.9-eclipse-temurin-21-noble AS builder

ARG REPO_ACCESS_TOKEN
ARG GIT_USER

SHELL ["/bin/bash", "-c"]

WORKDIR /licenseengine
COPY . /licenseengine

RUN git clone "https://$GIT_USER:$REPO_ACCESS_TOKEN@github.com/ffritze/Spdx-Java-Library-Rdf.git"

RUN cd Spdx-Java-Library-Rdf && mvn clean install -DskipTests -Dmaven.repo.local=/licenseengine/.m2

RUN cd /licenseengine

RUN mvn clean package -Dmaven.repo.local=/licenseengine/.m2

RUN rm -fr /licenseengine/.m2
    
FROM dhi.io/maven:3-jdk21-debian13-dev

WORKDIR /licenseengine
RUN mkdir /licenseengine/tmp
COPY --from=builder /licenseengine /licenseengine

ENTRYPOINT  ["java","-jar","target/licenseengine-0.0.3.jar"]