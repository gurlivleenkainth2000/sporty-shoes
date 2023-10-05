FROM openjdk:8
EXPOSE 9010
ADD target/sporty-shoes-v1.jar sporty-shoes-v1.jar
ENTRYPOINT ["java","-jar","/sporty-shoes-v1.jar"]