# product-demo
## Sample project utilizing Spring Boot and Reac.js

This project is a sample demo example. It simulates standard CRUD operations for dealing with a Product.
A Product has the following attributes:
- ID
- Name
- Description
- Tags - reusable for all products
- Price - with setting currency option
- Price points - representing the price in a specific currency (USD/GBP only).

### Prerequisites:<br/>
1. Java 1.8 <br/>
2. Maven 3.x <br/>

### How to run: <br/>
1. mvn package && java -jar target/product-demo-0.0.1-SNAPSHOT.jar <br/>
or <br/>
mvn spring-boot:run <br/>
2. In the browser go to http://localhost:8080.

### React.js client functionality:
- Crate a new product
- Get a list of all products
- Get details about a product
- Update a product

### A complete Asciidoc documentation of the REST API is [here](src/docs/asciidoc).

### Integration Testing
There are several test cases, testing the main app use cases.

### Considerations:
1. Security - There is no security functionality neither on client nor server side.<br/>
  1.1 Backend - A direct approach might be Spring Security Oauth or Spring Security plus custom session token logic.<br/>
  1.2 Client side security - utilize browser sandbox and local storage ...<br/>
  1.3 Transport protocol - HTTPS<br/>

2. This app follows Domain Driven Design paradigms. Logic is decoupled into several layers. Every layer should be responsible for the logic related to its layer. For example Service layer has to deal with the business logic and its logic should be preserved from migrating to Facade layer.
  * REST Facade - Deals with REST and DTOs.
  * Domain - Core business logic.
    * Service - Business services.
    * Model - Entities.
    * Repository - Repositories for Entities. <br/>
