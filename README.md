# gasto-erp

GastroERP – Restaurant Inventory & Cost Management System

Mini-ERP System for Restaurants built with Spring Boot & PostgreSQL.

Developed based on real-world experience in restaurant shift management.

Restaurants often struggle with:

  Uncontrolled food cost

  Missing stock transparency

  Manual inventory tracking

  Lack of KPI visibility

GastroERP provides:

  Ingredient-based stock tracking

  Recipe-driven automatic stock deduction

  Purchase & supplier management

  Food cost KPI calculation

  Stock movement audit trail

Tech Stack

  Java 21

  Spring Boot

  PostgreSQL

  JPA / Hibernate

  Docker

Architecture

  REST-based backend

  Transactional domain services

  Event-style stock movement model

  PostgreSQL relational database

Core Concept

  Auditability

  Traceability

  Financial transparency

  Status: WIP

## Getting Started

### Prerequisites

- Java 21
- Docker

### Start Database (Docker)

```bash
docker compose up -d
```

### Run Backend (Windows)

```powershell
cd backend
gradlew.bat bootRun
```

### Run Backend (macOS/Linux)

```bash
cd backend
./gradlew bootRun
```
