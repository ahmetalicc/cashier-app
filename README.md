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
