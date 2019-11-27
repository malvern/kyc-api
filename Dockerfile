FROM openjdk:8
VOLUME /tmp
ADD target/kyc-api-0.0.1-SNAPSHOT.jar /data/apps/services/kyc-api.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/data/apps/services/kyc-api.jar"]