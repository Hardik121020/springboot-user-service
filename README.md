📌 Problem Statement
Build a resilient backend microservice in Java (Spring Boot) that manages user metadata.
The service must expose two APIs:

API 1 — POST /user  
Create a new user with fields:

user_id

name

email

phone

created_at

API 2 — GET /user/{id}  
Return the stored user metadata.

🔒 Mandatory Reliability Requirements
Idempotency — Same request should not create duplicates.

Retry with exponential backoff + jitter — Applied when DB writes fail.

Circuit breaker — Protects the database layer from cascading failures.

Metrics recorded:

total_requests

success_count

failure_count

request_latency_ms

Structured logging:

Request ID

Latency

Error summary

Containerization — Service packaged with Docker for portability.
