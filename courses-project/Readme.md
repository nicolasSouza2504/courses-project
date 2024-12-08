# 🌐 Full-Stack Courses Platform

## 📝 Overview
This project is a full-stack application designed to manage user registrations, authentication, and course enrollments. It integrates a **React frontend**, **Spring Boot backend**, **RabbitMQ messaging**, **PostgreSQL databases**, and **JWT authentication**. The entire system is containerized using **Docker** for seamless deployment.

---

## 📂 Project Structure

- **Frontend**: React application for user interaction.
- **Backend**: Spring Boot service for authentication and user management.
- **Backend API**: Spring Boot service for course logic and enrollment.
- **Infrastructure**:
    - **RabbitMQ**: Messaging queue for asynchronous communication.
    - **PostgreSQL**: Persistent storage for user, course, and subscription data.

---


## 🔄 API and Backend Responsibilities

### Backend API (Port 8181)
- The **Backend API** is responsible for implementing the **business logic** for courses and subscriptions.
  - **Manages Course Logic**: Handles creation, listing, and subscription processes for courses.
  - **Concurrency Control**: Ensures consistency with subscriptions using mechanisms like `ReentrantLock`.

### Backend (Port 8080)
- The **Backend** serves as a **gateway** to facilitate communication between the **frontend** and the **Backend API**.
  - **Routes API Requests**: Passes user authentication, registration, and course-related requests from the frontend to the appropriate API endpoint.
  - **Manages Authentication**: Handles user login and JWT token generation.

This layered architecture ensures:
- Separation of concerns.
- Scalability and easier maintenance of code.
- Optimized communication between system components.

---

## 🚀 Getting Started

### Prerequisites
- Install **Docker** and **Docker Compose**.

---

## 🛠️ Configuration Details

### Docker Compose Services
```yaml
version: '3.8'
services:
  db:
    image: postgres:13
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=dbcourses
      - POSTGRES_DB=courses-db-web
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      - RABBITMQ_DEFAULT_USER=guest
      - RABBITMQ_DEFAULT_PASS=guest

  backendapi:
    build: ./back-end-api
    ports:
      - "8181:8181"
    depends_on:
      - db
      - rabbitmq

  backend:
    build: ./back-end
    ports:
      - "8080:8080"
    depends_on:
      - backendapi
      - db
      - rabbitmq

  front-end:
    build: ./front-end
    ports:
      - "3000:3000"
    depends_on:
      - backend
```

## 🏗️ Build and Start Services

### Start the Services
To start all the services, run the following command:

```bash
docker-compose up --build
```


### Access the Services

- **Frontend**: [http://localhost:3000](http://localhost:3000)
- **Backend**: [http://localhost:8080](http://localhost:8080)
- **Backend API**: [http://localhost:8181](http://localhost:8181)
- **RabbitMQ Management Console**: [http://localhost:15672](http://localhost:15672)  
  *(User: `guest`, Pass: `guest`)*

---

## 📚 Endpoints

### Backend Endpoints (Port 8080)

#### **User Management**

- **POST** `/user`  
  **Description**: Registers a new user.  
  **Request**:
  ```json
  {
    "name": "John Doe",
    "email": "johndoe@example.com",
    "cpf": "123.456.789-10",
    "birthDate": "1990-01-01",
    "password": "Strong@Password1"
  }
    ```
  **Response**:
  ```json
  {
  "id": 1,
  "email": "johndoe@example.com",
  "nome": "John Doe"
  }

    ```

- **POST** `/auth/login`  
  **Description**: Authenticates a user and generate a JWT token.  
  **Request**:
  ```json
  {
    "email": "johndoe@example.com",
    "password": "Strong@Password1"
  }

    ```
  **Response**:
  ```json
  {
     "message": "Login Successful",
     "data": {
       "id": 1,
       "token": "jwt-token",
       "cpf": "123.456.789-10"
     }
  }

    ```

- **DELETE** `/user/{id}`  
  **Description**: Deletes a user by ID.

  **Response**: Status: 204 No Content

## Backend API Endpoints (Port 8181)

### Course Management

#### **POST** `/courses`
**Description**: Creates a new course.  
**Authentication Required**  
**Request**:
```json
{
  "name": "Introduction to Programming",
  "avaiableSubscribes": 30
}

```

**Response**:
  ```json
 {
    "courseId": 1,
    "message": "Curso criado com sucesso"
 }

```

#### **GET** `/courses`
**Description**: Retrieves all available courses.
**Authentication Required**  

**Response**:
  ```json
 [
   {
     "id": 1,
     "name": "Introduction to Programming",
     "totalSubscriptions": 10,
     "totalSubscribes": 30
   }
]
```


#### **POST** `/courses/subscribe`
**Description**: Subscribes a user to a course.
**Authentication Required**  
**Request**:
```json
{
  "cpf": "123.456.789-10",
  "idCourse": 1
}
```

**Response**: Status: 200 OK


---

## 🛡️ Security Features

### JWT Authentication
- All requests to protected endpoints require a valid JWT token in the `Authorization` header.
- Example:
  ```http
  Authorization: Bearer <token>

### Login Protection
- Accounts are locked for 5 minutes after 5 consecutive failed login attempts.
- This helps secure user accounts against brute-force attacks.


## 📖 Additional Information

### RabbitMQ Integration
- The system uses RabbitMQ to asynchronously communicate between the backend and backend API.
- This integration ensures scalability and decoupled communication.

### Database Schema
- Includes separate tables for:
    - Users
    - Courses
    - Subscriptions

### Centralized Error Handling

- Validation Failures: Helps the user correct input data.
- Server Errors: Provides clarity on internal issues.

### Concurrency Control for Subscriptions
- The system ensures that users cannot over-subscribe to courses by implementing lock-based concurrency control using ReentrantLock.
- This mechanism:
    - Prevents race conditions when multiple users attempt to subscribe to the same course simultaneously.
    - Guarantees data consistency and ensures that available seats are accurately tracked in real-time.

