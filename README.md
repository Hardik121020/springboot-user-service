# 
---
## 📌 **Task 1: Resilient User Metadata Service**
### **Languages Allowed**
- **Java**

### **Problem Statement**
Build a **Resilient “User Metadata Service”** as a backend microservice with two APIs:

#### **API 1 — POST `/user`**
Create a new user with fields:
- **user_id**
- **name**
- **email**
- **phone**
- **created_at**

#### **API 2 — GET `/user/{id}`**
Return the stored user metadata.

---

### 🔒 **Mandatory Reliability Requirements**
1. **Idempotency** — Same request should not create duplicates.  
2. **Retry with exponential backoff and jitter** — Applied when DB writes fail.  
3. **Circuit breaker** — Protects the database layer from cascading failures.  
4. **Metrics recorded**:  
   - **total_requests**  
   - **success_count**  
   - **failure_count**  
   - **request_latency_ms**  
5. **Logging requirements**:  
   - **Request ID**  
   - **Latency**  
   - **Error summary**  
6. **Containerization** — Package the service using **Docker** for portability.

---

## 📌 **Task 2: Self-Service Deployment Portal Backend**

### **Languages Allowed**
- **Java + Terraform**

### **Problem Statement**
Build a **Self-Service “Deployment Portal Backend”** that allows developers to register new microservices and automatically provision infrastructure.

#### **Feature — Register a New Microservice**
Inputs:
- **service_name**
- **team_name**
- **repo_url**

#### **Automatically Create**
- **ECR repository**  
- **IAM role for the service**  
- **Basic Kubernetes deployment manifest (template)**  
- **Jenkins/GitLab pipeline YAML**

---

## 📂 **Project Structure**
```
springboot-user-service/
├── .idea/
│   ├── .gitignore
│   ├── misc.xml
│   ├── modules.xml
│   ├── spring-user-service.iml
│   └── vcs.xml
├── DB_Scripts/
│   └── db_tables.sql
├── docker-compose.yml
├── Dockerfile
├── jars/
│   ├── a1-0.0.1-SNAPSHOT.jar
│   └── backend-dev-service-0.0.1-SNAPSHOT.jar
├── jenkines/
│   └── Jenkinsfile
├── README.md
├── spec/
│   ├── a1/
│   │   ├── .idea/
│   │   │   ├── .gitignore
│   │   │   ├── a1.iml
│   │   │   ├── artifacts/
│   │   │   │   └── a1_jar.xml
│   │   │   ├── compiler.xml
│   │   │   ├── encodings.xml
│   │   │   ├── jarRepositories.xml
│   │   │   ├── misc.xml
│   │   │   └── modules.xml
│   │   ├── a1/
│   │   │   ├── .gitattributes
│   │   │   ├── .gitignore
│   │   │   ├── .mvn/
│   │   │   │   └── wrapper/
│   │   │   │       └── maven-wrapper.properties
│   │   │   ├── mvnw
│   │   │   ├── mvnw.cmd
│   │   │   ├── pom.xml
│   │   │   └── src/
│   │   │       ├── main/
│   │   │       │   ├── java/
│   │   │       │   │   └── com/
│   │   │       │   │       └── assignment/
│   │   │       │   │           └── a1/
│   │   │       │   │               ├── A1Application.java
│   │   │       │   │               ├── User.java
│   │   │       │   │               ├── UserController.java
│   │   │       │   │               ├── UserRepository.java
│   │   │       │   │               └── UserService.java
│   │   │       │   └── resources/
│   │   │       │       ├── application.yaml
│   │   │       │       └── META-INF/
│   │   │       │           └── MANIFEST.MF
│   │   │       └── test/
│   │   │           └── java/
│   │   │               └── com/
│   │   │                   └── assignment/
│   │   │                       └── a1/
│   │   │                           └── A1ApplicationTests.java
│   │   └── out/
│   │       └── artifacts/
│   │           └── a1_jar/
│   │               └── a1.jar
│   └── backend-dev-service/
│       ├── .gitattributes
│       ├── .gitignore
│       ├── .mvn/
│       │   └── wrapper/
│       │       └── maven-wrapper.properties
│       ├── mvnw
│       ├── mvnw.cmd
│       ├── out/
│       │   └── artifacts/
│       │       └── backend_dev_service_jar/
│       │           └── backend-dev-service.jar
│       ├── pom.xml
│       └── src/
│           ├── main/
│           │   ├── java/
│           │   │   └── com/
│           │   │       └── assignment/
│           │   │           └── backend_dev_service/
│           │   │               ├── BackendDevServiceApplication.java
│           │   │               ├── controller/
│           │   │               │   └── PlatformController.java
│           │   │               ├── dto/
│           │   │               │   ├── ServiceRequest.java
│           │   │               │   └── ServiceResponse.java
│           │   │               ├── model/
│           │   │               │   └── ServiceMetadata.java
│           │   │               ├── repository/
│           │   │               │   └── ServiceRepository.java
│           │   │               └── service/
│           │   │                   └── PlatformService.java
│           │   └── resources/
│           │       ├── application.yaml
│           │       └── META-INF/
│           │           └── MANIFEST.MF
│           └── test/
│               └── java/
│                   └── com/
│                       └── assignment/
│                           └── backend_dev_service/
│                               └── BackendDevServiceApplicationTests.java
└── terraform/
    ├── main.tf
    ├── modules/
    │   ├── ecr/
    │   │   └── ecr.tf
    │   ├── iam/
    │   │   └── iam.tf
    │   └── k8s/
    │       └── manifest.tf
    ├── terraform.tf
    └── variables.tf
```
---

## ⚙️ **Setup & Run**

### 1. **Database**
```sql
CREATE TABLE user (
    user_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL
);
```

## **Build & Run for API**
```
mvn clean package
java -jar jars/a1-0.0.1-SNAPSHOT.jar
```

## **API Usage**

### 1. **Create User**
```
curl -X POST http://localhost:8080/user \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Hardik Bansal",
           "email": "hardik@example.com",
           "phone": "9876543210"
         }'

```

### 2. **Get User by ID**
```
curl http://localhost:8080/user/{id}
```

## **🐳 Docker**
```
docker build -t user-service .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/postgres \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=mysecretpassword \
  user-metadata-service
```
## ⚙️ **Setup & Run for Backend Service**
### 1. **Backend Service**
```
mvn clean package
java -jar target/platform-engineering-assignment-0.0.1-SNAPSHOT.jar \
  -Dterraform.dir=/home/hardik/terraform \
  -Djenkins.url=http://localhost:8080/job/deploy/build \
  -Djenkins.user=admin \
  -Djenkins.token=myjenkinstoken
```

### 2. **API Usage**
- **Register Service**
```
curl -X POST http://localhost:8080/platform/register-service \
     -H "Content-Type: application/json" \
     -d '{
           "serviceName": "user-service",
           "teamName": "platform-team",
           "repoUrl": "https://github.com/example/user-service.git"
         }'
```
### 3. **Terraform Automation**
```
terraform apply -auto-approve \
  -var service_name=user-service \
  -var team_name=platform-team \
  -var repo_url=https://github.com/example/user-service.git
```
### 4. **Jenkins Trigger**
**Backend calls Jenkins REST API after Terraform completes:**
```
jenkins:
  url: http://localhost:8080/job/deploy/build
  user: admin
  token: myjenkinstoken
```

## **🛠️ Automation Flow**

1. **User hits backend API (POST /platform/register-service).**
2. **Backend triggers Terraform → provisions infra + generates Jenkinsfile.**
3. **Backend calls Jenkins REST API → Jenkins job starts.**
4. **Jenkins pipeline executes → builds, scans, pushes image, updates K8s manifest, deploys.**
5. **Metrics & logs collected for observability.**
