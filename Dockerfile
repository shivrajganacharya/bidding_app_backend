FROM openjdk:11
ADD target/biddingapp-spring-boot.jar biddingapp-spring-boot.jar
EXPOSE 8085
CMD ["java", "-jar", "biddingapp-spring-boot.jar"]

