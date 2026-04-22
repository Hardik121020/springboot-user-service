# **Resilient User Metadata Service**

## 📌 **Problem Statement**
Build a resilient backend microservice in **Java (Spring Boot)** that manages user metadata.  
The service must expose two APIs:

- **API 1 — POST `/user`**  
  Create a new user with fields:  
  - **user_id**  
  - **name**  
  - **email**  
  - **phone**  
  - **created_at**  

- **API 2 — GET `/user/{id}`**  
  Return the stored user metadata.

---

## 🔒 **Mandatory Reliability Requirements**
1. **Idempotency** — Same request should not create duplicates.  
2. **Retry with exponential backoff + jitter** — Applied when DB writes fail.  
3. **Circuit breaker** — Protects the database layer from cascading failures.  
4. **Metrics recorded**:  
   - **total_requests**  
   - **success_count**  
   - **failure_count**  
   - **request_latency_ms**  
5. **Structured logging**:  
   - **Request ID**  
   - **Latency**  
   - **Error summary**  
6. **Containerization** — Service packaged with Docker for portability.

---

## 📂 **Project Structure**
