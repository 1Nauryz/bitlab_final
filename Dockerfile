FROM openjdk:17-oracle
MAINTAINER "messenger"
COPY build/libs/cc-0.0.1-SNAPSHOT.jar bucket-buddy.jar
ENTRYPOINT ["java", "-jar", "bucket-buddy.jar"]