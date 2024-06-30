# Toyota Spring Boot Backend Microservice Project
This repository is a microservice project developed with spring boot that Toyota and 32bit companies want for their recruitment process.
## **Table of Content**
* [About the Project](#about-the-project)
* [Used Technologies](#used-technologies)
* [Important Points](#important-points)
* [Desing of Database](#design-of-database)
* [Project Architecture](#project-architecture)
* [Microservice Architecture](#microservice-architecture)
   - [API Gateway](#api-gateway)
   - [Discovery Server](#discovery-server)
   - [Authentication Service](#authentication-service)
   - [Product Service](#product-service)
   - [Report Service](#report-service)
   - [Sale Service](#sale-service)
   - [User Management Service](#user-management-service)
* [Unit Tests](#unit-tests)
* [Monitoring](#monitoring)
   - [Prometheus](#prometheus)
   - [Grafana](#grafana)
* [Continuous Integration](#continuous-integration)
   - [Jenkins](#jenkins)
* [Docker](#docker)
   - [Dockerhub](#dockerhub)
   - [Docker Compose](#docker-compose)
* [How to Run](#how-to-run)
   - [Running Locally](#running-locally)
   - [Running with Docker](#running-with-docker)
* [Contact](#contact)

## **About the Project**
This project is based on a general grocery shopping system. This system consists of different roles and operations based on these roles. There are 3 different role definitions in the system. Admin, store manager and cashier. The store manager is responsible for reporting, the cashier is responsible for shopping transactions, and the admin is responsible for operations such as adding and deleting roles. There are 5 different service which have different functionalities to provide users to be able to do their jobs.

In summary, the project, which contains different roles and different transaction permissions for these roles, is a market project developed with spring boot framework and microservice architecture.

## **Used Technologies**
* JDK17
* Spring Boot 2.7.18
   - Spring Web
   - Spring Data JPA
* Maven 4.0.0
* PostgreSQL
* Lombok
* Log4j2
* JUnit5
* Jaspersoft
* Prometheus & Grafana
* Jenkins
* Docker
* Spring Cloud (Api-Gateway, Eureka, Zipkin)

## **Important Points**
* Using Spring Boot Framework
* Layered Architecture
* Object Oriented Programming
* Compatible with SOLID principles
* Token based authentication and authorization
* Logging
* Unit Tests
* Java Doc
* Microservice Architecture and dockerization

## **Design of Database**
- User and Role Tables:
  - There are 3 roles in the system and there can be more than one user. A user can have one or more roles, and a role can belong to more than one user. Therefore, there must be many-to-many relationships between these tables. While the users table keeps users' name, email, password, activeness and username information, the roles table only keeps role name information.
- Sale, Products, Categories, Campaigns and Sold_Product Tables:
  - We will examine the remaining 5 tables here. First table is sale table. Sale table holds cashier name, change amount, payment type, received amount, total amount, sale time and time information of sales while sold_product table holds quantity, campaign id, product id and sale id information. In one sale there can be more than one sold product. For this reason we are establishing one-to-many relationship between sale and sold_product table.
  - Product table holds barcode, brand, expiration date, image, description, name, price, stock and category id while campaings table holds description, name and category id information. There can be more than one sold  product which belongs to one product or one campaign. So we are establishing again one-to-many relationship between products & sold_product and campaigns & sold_product tables.
  - Last table is categories table. Categories table holds description and name information. Because of a category can contain multiple different products and multiple different campaigns we are establishing one-to-many relationship between categories & products and categories & campaigns tables.

    ![Ekran görüntüsü 2024-06-18 143245](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/2c699944-ee7d-4097-8d96-0e813ea8a5b4)

## **Project Architecture**

Project Architecture consist of 5 layers.

   ![Ekran görüntüsü 2024-06-19 011830](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/962776a6-7805-4a89-a1b9-d053a15a31ed)

### Resource

Resource is an interface that allows software applications to communicate with each other. It defines the methods and data formats that applications can use to request and exchange information, enabling different software components to work together and share data efficiently.

### Domain

In software development, a domain (or entity) refers to a specific area of knowledge or activity that a particular application is concerned with. It represents real-world concepts or objects, along with their attributes and behaviors, that the software system models and manages.

### DAO

A design pattern that provides an abstract interface for accessing and manipulating data from a database or other persistent storage. It encapsulates the data access logic, making it easier to manage and maintain.

### Service

A layer in an application that contains business logic and operations. It acts as an intermediary between the controller (handling user inputs) and the DAO (handling data persistence), coordinating the data flow and enforcing business rules.

### DTO

An object that carries data between processes. It is used to transfer data between different layers of an application (such as between the service layer and the presentation layer) without exposing the internal details of the entity objects.

## **Microservice Architecture**


![Ekran görüntüsü 2024-06-19 191759](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/d4c1449c-6657-436d-9610-9ad0a69f67ca)

### API Gateway

An API Gateway is a server that acts as an entry point for external clients to access microservices. It is responsible for routing requests, aggregating responses, and often includes functionalities such as authentication, authorization, load balancing, rate limiting, and monitoring. Essentially, the API Gateway provides a unified interface for clients to interact with a complex microservices architecture, simplifying client-side code and managing cross-cutting concerns.

### Discovery Server

A Discovery Server, also known as a Service Registry, is a centralized server that maintains a registry of all the microservices in the application. Each microservice registers itself with the Discovery Server at startup, providing its network location (IP address and port). The Discovery Server enables service discovery, allowing microservices to find and communicate with each other without hard-coding network locations, thus facilitating dynamic scalability and resilience.

### Authentication Service

The Authentication Service is the central component where security transactions are performed. When a user logs in, the service verifies the user's credentials to ensure they are correct. The authentication and authorization structure is built using the JWT (JSON Web Token) library.

Upon successful login, a unique token is generated for the user. This token has a validity period, allowing the user to make requests to protected endpoints as long as the token remains unexpired. Additionally, the Authentication Service is responsible for embedding user roles within the token, which are then checked during role-based access control in the API Gateway.

### Product Service

This service is used to list the products in the system. No authorization is required for this service. It handles operations related to products, including listing, adding, and deleting products. Essentially, it coordinates all activities related to products.

### Report Service

This service lists the sales transactions. It should also be able to regenerate the receipt for any specific sale as a PDF. This service requires the role of a store manager. 

  ![Ekran görüntüsü 2024-06-19 160902](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/55c5ee40-e2e4-4d61-a05b-0cabd1708209)

### Sale Service

Sold products, total amount, and applied promotions should be recorded in the database by the sale service. Sales transactions can be conducted. The cashier role is required to perform these operations.

### User Management Service

There are three roles defined in the system: Cashier, Store Manager, and Admin. Roles are fixed, and a person can have one or multiple roles. It is mandatory for each person to have at least one role. So in this service there are operations for adding, updating, and deleting users. For deletions, a "soft delete" approach are applied. Only users with the Admin role can access the user management services.

## **Unit Tests**

  ![Ekran görüntüsü 2024-06-19 165900](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/6e36dbdd-110c-444c-a604-63a352a622db)

## **Monitoring**

### Prometheus

Prometheus is an open-source monitoring and alerting toolkit originally built at SoundCloud. It is designed for reliability, scalability, and easy deployment, making it a popular choice for monitoring microservices architectures.


  ![Ekran görüntüsü 2024-06-19 181855](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/1af44d6b-ac08-4474-98ba-f50583e0bae2)

### Grafana

Grafana is an open-source analytics and visualization platform that integrates with various data sources, including Prometheus. It provides a web-based interface for creating, exploring, and sharing dashboards with interactive charts and graphs. Grafana supports querying data from different databases and tools, making it a versatile tool for monitoring and analyzing system performance and metrics.


  ![Ekran görüntüsü 2024-06-19 181927](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/ea46d648-7cfa-44b7-a2e3-947090d8f94d)

## **Continuous Integration**

### Jenkins

Jenkins is an open-source automation server widely used for continuous integration (CI) and continuous delivery (CD) pipelines. It automates the process of building, testing, and deploying software projects, making it easier for development teams to collaborate and deliver code changes more efficiently. Jenkins supports integration with various version control systems, build tools, and testing frameworks, allowing for flexible and customizable automation workflows.


  ![Ekran görüntüsü 2024-06-19 183532](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/cd3a3ea0-b5d5-40b9-b413-d6321b563e0f)

## **Docker**

Docker is an open-source platform that enables developers to automate the deployment, scaling, and management of applications within lightweight, portable containers.

### Dockerhub

You can access my dockerhub repository by [clicking here](https://hub.docker.com/u/ahmetalicc) to be able to see all images of the project.

### Docker Compose

Docker Compose is a tool that allows users to define and manage multi-container Docker applications. With Docker Compose, you can use a YAML file to configure your application’s services, networks, and volumes. This enables you to easily define, deploy, and run complex applications consisting of multiple interconnected containers, ensuring that they work together as intended. Docker Compose simplifies the orchestration and scaling of multi-container environments.

- Build images specified in Docker Compose configuration file

     `docker-compose build`

- Run the application with using Docker Compose configuration file. (Ensure that you set up the database connection in your local computer.)
  
     `docker-compose up`

- Stop the running application and remove all containers with using Docker Compose configuration file.

     `docker-compose down`
## **How to Run**
### Running Locally
**Prerequisites:**

- Ensure you have Java JDK 17 or later installed.
- Ensure you have Maven installed.

**Clone the Repository:**

```bash
git clone https://github.com/yourusername/kasiyerapp.git
cd yourproject
```
### Running with Docker

## **Contact**

This project is developed by Ahmet Alıç. Contact for the missing parts, different ideas, questions, and suggestions. 
(ahmetalicswe@gmail.com)

