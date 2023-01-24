

Tech Stacks:

Spring Boot and Spring microservices
Spring security and JWT.
Spring Cache Abstraction
Scheduling


This project is java-based. So It requires Jdk 8 (or later) and Maven 3.6.3 (or later) to run.

$ cd weather
$ mvn package

$mvn spring-boot :run OR

$ java -jar target/weather-0.0.1-SNAPSHOT.jar 


Improvement Areas
 Can Add Redis or Memcache
We can add AOP feature for Loging
Can Store request when service is down and respond once service is up based on request reconciliation.

Pending Task:
Need to Add Unit Test and Integration test.





