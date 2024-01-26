# Spring Boot REST API

This is a Java 17 / Maven / Spring Boot (version 3.2.2) application which is back end API for the Angular application https://github.com/theprogzone/restaurent-decider-ui.

## How to Run

### In command line

* Clone this repository
* Checkout to the ```master``` branch
* Make sure you are using JDK 17 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run this command to run the application: ```mvn spring-boot:run```

Once the application runs you should see something like this

```
2024-01-26T11:37:25.082+08:00  INFO 23004 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
2024-01-26T11:37:25.099+08:00  INFO 23004 --- [  restartedMain] c.g.r.RestaurantDeciderApplication       : Started RestaurantDeciderApplication in 6.057 seconds (process running for 6.361)
```

### In IntelliJ IDEA IDE

* Clone this repository to your IntelliJ IDEA project folder
* Open IntelliJ IDEA and choose "Open" from the main menu. Navigate to the directory where you cloned your Spring Boot project and select the project's root directory. Click "OK" to open the project.
* If IntelliJ IDEA detects the pom.xml file (indicating a Maven project), it will prompt you to import the project. Click on "Enable Auto-Import" if necessary.
* Locate the main class of the Spring Boot application (RestaurantDeciderApplication). This the class that have the @SpringBootApplication annotation. Right-click on the class file and select "Run RestaurantDeciderApplication."

Alternatively, you can use the Spring Boot run configuration:

* Open the "Run/Debug Configurations" from the top-right dropdown menu.
* Click on the "+" icon and choose "Spring Boot."
* Set the main class to your Spring Boot application class.
* Click "OK" to save the configuration.

After successfully running the application, you can check the application status using this url: http://localhost:8080/actuator/health


## About the Service

The service is a simple REST service for decide a restaurant for office teammates during their lunchtime. It uses an in-memory database (H2) to store the data. You can also do with a relational database like MySQL or PostgreSQL, You only need to change the database connection properties in the ```application.yml``` file and add the relevant maven dependency.

To secure the APIs, I have integrated the JWT authentication with this application.

All the APIs are "self-documented" using the springdoc-openapi. you can access this document using this url: http://localhost:8080/v3/api-docs

Here are the endpoints available in this service:

### Login

```
POST /example/v1/hotels
Accept: application/json
Content-Type: application/json

REQUEST:

{
    "username" : "someuser",
    "password" : "somepassword"
}

RESPONSE: 

HTTP 200

{
    "firstName" : "John",
    "lastName" : "Doe",
    "token" : "fsadfadfagadsgdgsadgghdafgadgdfgdfgdgdfgfdgfsddfsdfsdfdsfsf",
}

```

### Create / Terminate session

```
PUT /api/v1/session
Accept: application/json
Content-Type: application/json

REQUEST:

{
    "type" : "<start>/<stop>",
    "sessionId" : "40fa6777-b8f6-41b8-8454-b7bff309177a"
}

RESPONSE: 

HTTP 201

{
    "sessionId" : "40fa6777-b8f6-41b8-8454-b7bff309177a"
}

```

### Add restaurant

```
POST /api/v1/restaurant
Accept: application/json
Content-Type: application/json

REQUEST:

{
    "sessionId" : "40fa6777-b8f6-41b8-8454-b7bff309177a",
    "restaurantName" : "Sample restaurant name",
    "restaurantLocation" : "Restaurant location",
    "personName" : "Name of the person"
}

RESPONSE: 

HTTP 201

```

### List all the restaurants created under a session

```
GET /api/v1/restaurant?sessionId=<session_id>

RESPONSE: 

HTTP 200

{
    "restaurantList" : [
        {
            "restaurantName" : "Sample restaurant name",
            "restaurantLocation" : "Restaurant location",
            "personName" : "Name of the person"
        }, 
        {
            "restaurantName" : "Sample restaurant name",
            "restaurantLocation" : "Restaurant location",
            "personName" : "Name of the person"
        }
    ],
    "selectedRestaurant" : {
        "restaurantName" : "Sample restaurant name",
        "restaurantLocation" : "Restaurant location",
        "personName" : "Name of the person"
    }
}

```

### To view your H2 in-memory datbase

In this application we are using H2 in-memory database. To view and query the database you can browse to http://localhost:8090/h2-console. Default username is 'sa' with 'password' as the password.

## Special notes

* For the time being, I'm keeping some credentials and secret keys in the '''application.yml''' file. In the production level we can keep them in a secure store like aws secrets manager.
* No user registration API has been implemented in the application yet. So until I'm implementing user registration api, we can use below credentials for the authentication purposes.

Username : ```admin```
Password : ```admin1234```
