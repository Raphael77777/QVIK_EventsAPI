# QVIK_EventsAPI
This project is a Spring Boot application built using Maven.
  ## Learn more about
  * [Spring Boot](https://spring.io/projects/spring-boot)
  * [Maven](https://maven.apache.org/)
  
## Deployed by Heroku
* API developed from this project can be accessed by [Deployed URL](https://qvik.herokuapp.com/api/v1/)
* API Definiation can be found at [Swagger API Definition](https://qvik.herokuapp.com/swagger-ui/index.html?configUrl=/api-docs/swagger-config)
* API Doc can be found at [API Doc](https://qvik.herokuapp.com/swagger-ui/index.html?configUrl=/api-docs/swagger-config)


## Running locally
You can build a jar file and run it from the command line:

```
git clone https://github.com/Raphael77777/QVIK_EventsAPI.git
cd QVIK_EventsAPI
./mvnw spring-boot:run
java -jar target/*.jar
```

## Working on the IDE
### Prerequisites
* Java 8 or newer
* git command line tool
* your preferred IDE: Eclipse, IntelliJ IDEA, VS Code


### Steps
1. On the command line
```
git clone https://github.com/Raphael77777/QVIK_EventsAPI.git
```

2. Open the project on your IDE and Run the project
3. Visit http://localhost:8080/api/v1/ in your browser.


## Database
* This project is based on the RDBMS and used PostgreSQL database for the deployment. 
* During the project, sample data were produced to test GET API calls for mobile application. 
* Sample data can be seen, updated or deleted in the main class (i.e., EventsApplication.java)  
* The ERD looks like below:
<img width="80%" src="https://user-images.githubusercontent.com/69889362/116440284-fff88e80-a858-11eb-9be4-a40c3ca27ed1.png"/>


### Setup Database
* This projoect used JPA and all the configuration can be found at application.properties 
* To be specific, you can change database by changing below:
```
server.port=
spring.jpa.database=
spring.datasource.platform=
spring.datasource.url=jdbc:
spring.datasource.username=
spring.datasource.password=
```



## Contirubutor
* Design:Jaakko Junttila 
* Front end: @nkzinovyeva @ColineFardel @pthambirajah
* Back end: @Raphael77777 @taeheelee90
