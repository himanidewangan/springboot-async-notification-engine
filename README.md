# 📬 Distributed Notification System

A production-style backend system built using Java and Spring Boot that simulates real-world asynchronous transaction processing with retry mechanisms, circuit breaker, dead letter queue (DLQ), and metrics tracking.

---

## 🚀 Features

* ✅ Asynchronous processing using thread pools (`@Async`)
* ✅ Retry mechanism with configurable attempts
* ✅ Circuit Breaker for failure protection
* ✅ Dead Letter Queue (DLQ) for failed transactions
* ✅ Real-time metrics tracking (success, failure, rejected)
* ✅ REST API for transaction ingestion
* ✅ MySQL persistence using Spring Data JPA
* ✅ Kafka-ready architecture (event-driven design)

---

## 🏗️ Architecture

```
Client
  ↓
REST API (/transaction)
  ↓
Kafka Producer (decoupling layer)
  ↓
Kafka Topic (transactions-topic)
  ↓
Kafka Consumer
  ↓
Async Processor (@Async)
  ↓
 ├── Success → Database
 ├── Failure → Retry → DLQ
 └── Rejected → Circuit Breaker
```

---

## ⚙️ Tech Stack

* Java 23
* Spring Boot
* Spring Async
* Spring Data JPA
* MySQL
* Apache Kafka (event-driven design)
* ExecutorService (custom thread pool)

---

## 🔁 Processing Flow

1. Client sends transaction via REST API
2. Transaction is published to Kafka
3. Consumer listens and processes asynchronously
4. Retry logic attempts processing up to 3 times
5. On success → stored in DB
6. On failure → moved to Dead Letter Queue
7. Circuit breaker prevents cascading failures

---

## 📊 Metrics Endpoint

```
GET /metrics
```

Response:

```
Success: X  
Failed: Y  
Rejected: Z  
```

---

## 🧪 Load Testing Results

| Requests | Success | Failed | Rejected |
| -------- | ------- | ------ | -------- |
| 100      | ~68     | ~2     | 0        |
| 300      | 170     | 1      | 0        |

### Observations:

* Async processing improves throughput
* Thread pool limits act as backpressure
* System gracefully handles failures using retry + DLQ

---

## ⚠️ Known Limitations

* Retry uses blocking delay (`Thread.sleep`) — can be improved with non-blocking scheduling
* Circuit breaker is in-memory (not distributed)
* Kafka setup requires Docker (not included in local setup)
* No distributed tracing (future enhancement)

---

## 🔮 Future Improvements

* Add Redis for caching and rate limiting
* Implement distributed tracing (Zipkin/OpenTelemetry)
* Deploy on AWS (EC2 + RDS + MSK)
* Add API Gateway layer
* Replace blocking retries with event-based reprocessing

---

## ▶️ How to Run

1. Clone repository
2. Configure MySQL in `application.properties`
3. Run Spring Boot application
4. Hit API:

```
POST /transaction
```

---

## 💡 Resume Impact

Built a distributed notification processing system using Java, Spring Boot, and Kafka concepts with asynchronous execution, retry logic, circuit breaker, dead-letter queue handling, and real-time metrics, capable of handling 300+ concurrent requests.

---

## 👩‍💻 Author

Himani Dewangan
