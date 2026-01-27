FROM maven:3-amazoncorretto-17-debian-trixie AS builder

WORKDIR /licenseengine
COPY . /licenseengine

RUN mvn clean package -DskipTests   
    
FROM maven:3-amazoncorretto-17-debian-trixie

WORKDIR /licenseengine
COPY --from=builder /licenseengine /licenseengine

ENTRYPOINT  ["java","-jar","target/licenseengine-0.0.2-SNAPSHOT.jar"]