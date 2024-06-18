# Toyota Spring Boot Backend Microservice Project
This repository is a microservice project developed with spring boot that Toyota and 32bit companies want for their recruitment process.
### Table of Content
* [About the Project](#about-the-project)
* [Used Technologies](#used-technologies)
* [Points of Attention](#points-of-attention)
* [Desing of Database](#design-of-database)
* [Application Architecture](#application-architecture)
* [Security](#security)
* [Microservice Architecture](#microservice-architecture)
* [Unit Tests](#unit-tests)
* [Monitoring](#monitoring)
* [Continuous Integration](#continuous-integration)
* [Docker](#docker)
* [Contact](#contact)

### About the Project
This project is based on a general grocery shopping system. This system consists of different roles and operations based on these roles. There are 3 different role definitions in the system. Admin, store manager and cashier. The store manager is responsible for reporting, the cashier is responsible for shopping transactions, and the admin is responsible for operations such as adding and deleting roles. There are 5 different service which have different functionalities to provide users to be able to do their jobs.

In summary, the project, which contains different roles and different transaction permissions for these roles, is a market project developed with spring boot framework and microservice architecture.

### Used Technologies
* JDK17
- Spring Boot 2.7.18
   - Spring Web
   - Spring Data JPA
* Maven 4.0.0
* PostgreSQL
* Lombok
* Log4j2
* JUnit5
* Prometheus & Grafana
* Jenkins
* Docker
* Spring Cloud (Api-Gateway, Eureka, Zipkin)

### Points of Attention
* Using Spring Boot Framework
* Layered Architecture
* Object Oriented Programming
* Compatible with SOLID principles
* Token based authentication and authorization
* Logging
* Unit Tests
* Java Doc
* Microservice Architecture and dockerization

### Design of Database
- User and Role Tables:
  - There are 3 roles in the system and there can be more than one user. A user can have one or more roles, and a role can belong to more than one user. Therefore, there must be many-to-many relationships between these tables. While the users table keeps users' name, email, password, activeness and username information, the roles table only keeps role name information.
- Sale, Products, Categories, Campaigns and Sold_Product Tables:
  - We will examine the remaining 5 tables here. First table is sale table. Sale table holds cashier name, change amount, payment type, received amount, total amount, sale time and time information of sales while sold_product table holds quantity, campaign id, product id and sale id information. In one sale there can be more than one sold product. For this reason we are establishing one-to-many relationship between sale and sold_product table.
  - Product table holds barcode, brand, expiration date, image, description, name, price, stock and category id while campaings table holds description, name and category id information. There can be more than one sold  product which belongs to one product or one campaign. So we are establishing again one-to-many relationship between products & sold_product and campaigns & sold_product tables.
  - Last table is categories table. Categories table holds description and name information. Because of a category can contain multiple different products and multiple different campaigns we are establishing one-to-many relationship between categories & products and categories & campaigns tables.

    ![Ekran görüntüsü 2024-06-18 143245](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/2c699944-ee7d-4097-8d96-0e813ea8a5b4)

### Project Architecture

Project Architecture consist of 5 layers.

   ![Ekran görüntüsü 2024-06-19 011830](https://github.com/ahmetalicc/kasiyerapp/assets/117573659/962776a6-7805-4a89-a1b9-d053a15a31ed)

#### Resource

Resource is an interface that allows software applications to communicate with each other. It defines the methods and data formats that applications can use to request and exchange information, enabling different software components to work together and share data efficiently.

#### Domain

In software development, a domain (or entity) refers to a specific area of knowledge or activity that a particular application is concerned with. It represents real-world concepts or objects, along with their attributes and behaviors, that the software system models and manages.

#### DAO

A design pattern that provides an abstract interface for accessing and manipulating data from a database or other persistent storage. It encapsulates the data access logic, making it easier to manage and maintain.

#### Service

A layer in an application that contains business logic and operations. It acts as an intermediary between the controller (handling user inputs) and the DAO (handling data persistence), coordinating the data flow and enforcing business rules.

#### DTO

An object that carries data between processes. It is used to transfer data between different layers of an application (such as between the service layer and the presentation layer) without exposing the internal details of the entity objects.

