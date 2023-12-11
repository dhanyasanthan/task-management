# Task Management System

This is a simple Task Management System built using Spring Boot. It allows users to create, update, delete, and view tasks associated with projects. Each task has attributes such as title, description, status, priority, and deadlines.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation and Run](#installation)

## Getting Started

### Prerequisites

- Java 8 or later
- Maven
- Your preferred IDE (IntelliJ, Eclipse, etc.)

### Installation and Run
1. Clone the repository:

git clone https://github.com/yourusername/task-management-system.git

2. Navigate to the project directory:
cd task-management-system

3.Build the project:
mvn clean install

4. Run the Spring Boot application:
mvn spring-boot:run

5. Access the application at http://localhost:8080 in your web browser.

6. Run JUnit tests for controllers and services. Run the tests with:
mvn test

### Docker
 docker build -t <image-name> .

### Javadoc
 In IDE for e.g. Intellij:Tools -> Generate JavaDoc -> Select putput doc folder -> Click on Generate.


