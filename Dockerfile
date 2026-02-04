FROM docker.io/maven:3.9-eclipse-temurin-17-noble AS builder

ARG REPO_ACCESS_TOKEN
ARG GIT_USER

WORKDIR /licenseengine
COPY . /licenseengine

RUN git clone --single-branch --branch "magicfix" "https://$GIT_USER:$REPO_ACCESS_TOKEN@github.tik.uni-stuttgart.de/ac143675/Spdx-Java-Library.git"

RUN cd Spdx-Java-Library && mvn clean install -DskipTests

RUN cd /licenseengine

RUN mvn clean package -DskipTests   
    
FROM docker.io/maven:3.9-eclipse-temurin-17-noble

WORKDIR /licenseengine
COPY --from=builder /licenseengine /licenseengine

ENTRYPOINT  ["java","-jar","target/licenseengine-0.0.3.jar"]