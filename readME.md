# Spring Security + JWT + MongoDB learning project

This project demonstrates:
- Spring Boot + Spring Security
- JWT-based authentication
- MySQL/JPA default setup
- MongoDB CRUD support for learning

I checked the workspace and the MongoDB classes are present here:
- `src/main/java/spring_security_model/security/mongodb/MongoEmployee.java`
- `src/main/java/spring_security_model/security/mongodb/MongoEmployeeRepository.java`
- `src/main/java/spring_security_model/security/mongodb/MongoEmployeeService.java`
- `src/main/java/spring_security_model/security/mongodb/MongoEmployeeController.java`
- `src/test/java/spring_security_model/security/mongodb/MongoEmployeeServiceTest.java`

## 1. Prerequisites

Install:
- Java 17+
- Maven
- Docker (recommended for MongoDB/MySQL)

Optional:
- Postman or curl

## 2. Project structure

Important classes:
- `src/main/java/spring_security_model/security/controller/AuthController.java` -> login API
- `src/main/java/spring_security_model/security/jwt/JwtTokenProvider.java` -> creates/validates JWT
- `src/main/java/spring_security_model/security/jwt/JwtTokenFilter.java` -> reads Authorization header and authenticates user
- `src/main/java/spring_security_model/security/entrysecurity/SecurityConfiguration.java` -> security rules
- `src/main/java/spring_security_model/security/entity/AppUser.java` -> user table
- `src/main/java/spring_security_model/security/entity/Employee.java` -> JPA employee entity
- `src/main/java/spring_security_model/security/mongodb/MongoEmployee.java` -> MongoDB employee document

## 3. Default app behavior (MySQL + JWT)

The project is configured to run with MySQL by default. The main DB config is in:
- `src/main/resources/application.properties`

Update DB credentials if needed:
- username
- password
- url if you use a different port or database name

Example MySQL via Docker:
```bash
docker run -d \
  --name mysql-security \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=employee_db \
  -p 3306:3306 \
  mysql:8.0
```

Then run:
```bash
./mvnw clean spring-boot:run
```

The app starts on default port `8080`.

## 4. JWT flow in this project

Flow:
1. Client sends `POST /auth/login` with username/password
2. `AuthController` calls `AuthenticationManager.authenticate(...)`
3. Spring Security validates username/password against `CustomUserDetailsService`
4. If successful, `JwtTokenProvider.generateToken(...)` creates a JWT
5. Client receives token in response
6. For later requests, client sends:
```http
Authorization: Bearer <token>
```
7. `JwtTokenFilter` reads the header, validates the token, and sets security context
8. Protected APIs are accessed without sending username/password again

Example login request:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"ali","password":"ali123"}'
```

Response example:
```json
{
  "accessToken": "eyJ...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

Then call a protected API:
```bash
curl http://localhost:8080/employees/all \
  -H "Authorization: Bearer <token>"
```

## 5. MongoDB setup with Docker

Start MongoDB:
```bash
docker run -d \
  --name mongo-security \
  -p 27017:27017 \
  mongo:7
```

The Mongo config is in:
- `src/main/resources/application-mongo.properties`

This file sets:
```properties
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
spring.data.mongodb.uri=mongodb://localhost:27017/spring_security_mongo_db
spring.data.mongodb.auto-index-creation=true
server.port=8081
```

Run the app with Mongo profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mongo
```

Then Mongo endpoints are available on port `8081`.

## 6. MongoDB CRUD endpoints

Available endpoints:
- `POST /mongo/employees`
- `GET /mongo/employees`
- `GET /mongo/employees/{id}`
- `PUT /mongo/employees/{id}`
- `DELETE /mongo/employees/{id}`

Create document:
```bash
curl -X POST http://localhost:8081/mongo/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Ali","email":"ali@gmail.com","department":"IT"}'
```

Get all:
```bash
curl http://localhost:8081/mongo/employees
```

Get one:
```bash
curl http://localhost:8081/mongo/employees/{id}
```

Update:
```bash
curl -X PUT http://localhost:8081/mongo/employees/{id} \
  -H "Content-Type: application/json" \
  -d '{"name":"Ali Khan","email":"ali@gmail.com","department":"Engineering"}'
```

Delete:
```bash
curl -X DELETE http://localhost:8081/mongo/employees/{id}
```

## 7. How to test the complete project

### A. Test MySQL + JWT flow
1. Start MySQL container
2. Start app without profile:
```bash
./mvnw spring-boot:run
```
3. Login:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"ali","password":"ali123"}'
```
4. Save the returned token
5. Call protected APIs with bearer token

### B. Test Mongo CRUD flow
1. Start Mongo container
2. Run with Mongo profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mongo
```
3. Create, fetch, update and delete employees through `/mongo/employees`

### C. Test JUnit service layer
```bash
./mvnw test
```
This runs the project test suite and also executes the Mongo service unit tests.

## 8. Important notes

- The app uses both JPA and MongoDB dependencies together. That is fine for learning, but in production you usually choose one database per service.
- The Mongo profile disables the MySQL DataSource auto-configuration, so Mongo and MySQL do not conflict when you run the app with the `mongo` profile.
- If your local MySQL password is different, update `src/main/resources/application.properties` before running the default app.
- For real-world usage, use environment variables instead of hardcoding secrets.

## 9. Simple learning mindset

- MySQL/JPA: good for relational tables and complex joins
- MongoDB: good for document-based JSON-style data
- JWT: good for stateless authentication across services

## 10. Common commands summary

Start MySQL:
```bash
docker run -d --name mysql-security -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=employee_db -p 3306:3306 mysql:8.0
```

Start Mongo:
```bash
docker run -d --name mongo-security -p 27017:27017 mongo:7
```

Run default app:
```bash
./mvnw spring-boot:run
```

Run Mongo profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mongo
```

Run tests:
```bash
./mvnw test
```

## 11. Why this project is useful for learning

This example connects three important ideas:
- Spring Security for authentication/authorization
- JWT for stateless login sessions
- MongoDB for document-based CRUD operations

That makes it a good project to understand how real-world backend systems work together.
