
# 🎓 University Management System

## 📝 Description

This project is a RESTful API developed in Java with Spring Boot, aimed at managing students, professors, coordinators, courses, and subjects in a university. It implements CRUD operations for these entities and has different access levels for students, professors, and coordinators based on their roles.

## 📑 Index

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Database Configuration](#database-configuration)
- [How to Run the Project](#how-to-run-the-project)
- [Folder Structure](#folder-structure)

## 🚀 Features

- **Student Registration:** Allows registering students with information such as name, email, birthdate, and course.
- **Professor Registration:** Management of professors and the subjects they teach.
- **Coordinator Registration:** Associates coordinators with courses and allows them to also teach subjects.
- **Subject Management:** Adds subjects with student limits and assigns both main and substitute professors.
- **Student Enrollment:** Enables students to enroll in courses and subjects.
- **Reports:** Generates detailed reports on students, professors, coordinators, and courses.

## 🧰 Prerequisites

Make sure you have the following tools installed:

- [Java JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [MySQL or PostgreSQL](https://dev.mysql.com/downloads/installer/): A running and configured database.
- [Maven](https://maven.apache.org/install.html): To build and manage the project dependencies.
- [Docker](https://www.docker.com/): For setting up the database using a container.

## 🛠 Database Configuration

1. Create a MySQL/PostgreSQL database using Docker (or create it manually):
   
   ```bash
   docker run --name university-db -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=university_db -p 3306:3306 -d mysql:latest
   ```

2. In the `application.properties` file, configure the database access credentials:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/university_db
   spring.datasource.username=root
   spring.datasource.password=admin
   ```

3. Run the project so that the tables are automatically created in the database.

## 🚀 How to Run the Project

1. **Clone the repository:**

   ```bash
   git clone https://github.com/VictorHugoCC/SP_SpringBoot_AWS_Desafio_02.git
   cd SP_SpringBoot_AWS_Desafio_02
   ```

2. **Build the project with Maven:**

   ```bash
   mvn clean install
   ```

3. **Run the project:**

   ```bash
   mvn spring-boot:run
   ```

4. **Access the API Swagger documentation at:**

   ```
   http://localhost:8080/swagger-ui.html
   ```

## 📂 Folder Structure

### **Entities**

- **Student:** Represents students enrolled in courses.
- **Professor:** Stores information about professors, their subjects, and courses.
- **Coordinator:** Represents coordinators responsible for the courses.
- **Course:** Contains information about the courses offered at the university.
- **Subject:** Contains information about subjects, with main and substitute professors.
- **Enrollment:** Relationship between students and courses.
- **User:** Serves as an inheritance entity.

### **Services**

- **StudentService:** Responsible for the business logic of student registration and management.
- **ProfessorService:** Handles the management of professors and subject assignments.
- **CoordinatorService:** Deals with the association of coordinators with courses and the conversion of coordinators to professors.
- **CourseService:** Manages courses, creation, updating, and subject linking.
- **SubjectService:** Manages subjects and associations with professors and students.
- **EnrollmentService:** Handles the logic of enrolling students in courses.

### **Repositories**

- **StudentRepository:** Interface responsible for student data persistence.
- **ProfessorRepository:** Interface responsible for professor data persistence.
- **CoordinatorRepository:** Interface responsible for coordinator data persistence.
- **CourseRepository:** Interface responsible for course data persistence.
- **SubjectRepository:** Interface responsible for subject data persistence.
- **EnrollmentRepository:** Interface responsible for enrollment persistence.

### **Controllers**

- **StudentController:** Exposes endpoints for creating and managing students.
- **ProfessorController:** Manages professor endpoints and their subjects.
- **CoordinatorController:** Manages coordinator endpoints, allowing them to also be assigned as professors.
- **CourseController:** Exposes endpoints for creating and managing courses.
- **SubjectController:** Manages endpoints for subject management and association with professors and students.

### **DTOs**

- **StudentDTO:** Defines the data representations to be exposed or received by the API for students.
- **ProfessorDTO:** Representations of professor data and their subjects.
- **CoordinatorTeachDTO:** DTO that allows the conversion of coordinators to professors of subjects.
- **CourseDTO:** Definitions of course data to be exposed or received.
- **SubjectDTO:** Representations of subjects, with their main and substitute professors.

### **Security**

- **JWTAuthenticationFilter:** Filter responsible for managing JWT authentication.
- **JWTAuthorizationFilter:** Filter responsible for authorizing access based on JWT tokens.
- **SecurityConfig:** Security configuration, defining access rules for different profiles (student, professor, coordinator).

## 💡 Tests

- Implement unit tests with JUnit to cover the main functionalities of the API.
- Implement integration tests to ensure the correct functioning of business flows.

## 📄 Documentation

- **Swagger UI:** For automatic API documentation and direct testing from the interface.

  ```
  http://localhost:8080/swagger-ui.html
  ```
