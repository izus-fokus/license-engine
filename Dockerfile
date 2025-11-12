FROM maven:3.9.11-amazoncorretto-17-debian-trixie AS builder

WORKDIR /licenseengine
COPY . /licenseengine

RUN mvn clean package -DskipTests   
    
FROM maven:3.9.11-amazoncorretto-17-debian-trixie

COPY --from=builder /licenseengine/target/licenseengine-0.0.1-SNAPSHOT.jar licenseengine.jar

ENTRYPOINT  ["java","-jar","licenseengine.jar"]