# Pay My Buddy

PayMyBuddy is an application for sending money to his contact.

## About The Project

Web application prototype for Openclassrooms, project number 6.
This is a Spring Boot application.

## Getting Started

### Prerequisites

Check that you have :

* Apache Maven 3.8.6 installed
* Java 11 installed
* mySQL 8.0.30 installed

### Installation

1. Connect to your mysql Datasources
2. Run database.sql

3. Select the paymybuddy directory
   ```sh
   cd paymybuddy
   ```
4. Set environment variables "spring.datasource.url" -> jdbc:mysql://[YOUURLMysql]/paymybuddy?serverTimezone=UTC
5. Set environment variables "spring.datasource.username" -> your mySQL username
6. Set environment variables "spring.datasource.password" -> your mySql password

7. Execute
   ```sh
    mvn spring-boot:run
   ```
8. To access the application, open your browser, go to [http://localhost:9000](http://localhost:9000)

## Model Database (SQL)

![Model BDD](/img/diagrammeDataBase.png)

### UML Diagram

![Diagram UML](/img/DiagrammeClassP6.png)