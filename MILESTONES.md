GastroERP – Project Goals & Roadmap
🟢 Phase 1 – Project Setup

Initialize Spring Boot project (clean package structure)

Configure PostgreSQL with Docker

Setup database connection

Add Flyway for database migrations

Create initial database schema

🟢 Phase 2 – Core Domain Model
Product Management

Create Product entity (ingredients)

Implement CRUD endpoints for products

Add minimum stock field

Store purchase price per unit

Menu & Recipes (Bill of Materials)

Create MenuItem entity

Create RecipeLine entity (ingredient per dish)

Implement endpoints to assign ingredients to menu items

Ensure unique ingredient per menu item

Inventory System

Implement StockMovement entity

Define movement types (IN_RECEIPT, OUT_SALE, OUT_WASTE, ADJUSTMENT)

Implement stock aggregation logic (calculate current stock)

Add endpoint to view current inventory levels

🟢 Phase 3 – Business Logic
Sales

Implement Sale and SaleLine

Create endpoint to register a sale

Automatically deduct ingredients based on recipe

Ensure transactional consistency

Prevent or log negative stock

Purchasing

Implement Supplier entity

Implement PurchaseOrder and PurchaseOrderLine

Create endpoint for goods receipt

Increase stock via StockMovement

Stock Adjustments

Implement stocktake endpoint

Generate ADJUSTMENT movements

Track stock correction history

🟢 Phase 4 – Controlling & KPIs

Calculate Cost of Goods Sold (COGS)

Calculate Food Cost Percentage

Implement minimum stock alerts

Create dashboard endpoints (date-based filtering)

🟢 Phase 5 – Security & Professionalization

Implement role-based access control (ADMIN, MANAGER, SHIFTLEAD)

Secure endpoints using Spring Security

Add OpenAPI / Swagger documentation

Add global exception handling

Improve validation & error responses

🟢 Phase 6 – Deployment & Polish

Add Docker Compose for full system startup

Provide seed data for demo purposes

Write comprehensive README

Add architecture diagram (C4 Model)

Add example API requests

Setup GitHub Actions CI pipeline

🚀 Stretch Goals (Optional Advanced Features)

CSV import for daily sales

Multi-location support

Domain events for stock changes

Unit & integration tests

Performance optimization for stock queries

Basic frontend (React)