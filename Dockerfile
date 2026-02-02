FROM docker.io/maven:3.9.12-amazoncorretto-17-alpine AS builder

WORKDIR /licenseengine
COPY . /licenseengine

RUN mvn clean package -DskipTests   
    
FROM docker.io/maven:3.9.12-amazoncorretto-17-alpine

WORKDIR /licenseengine
COPY --from=builder /licenseengine /licenseengine

ENTRYPOINT  ["java","-jar","target/licenseengine-0.0.2-SNAPSHOT.jar"]