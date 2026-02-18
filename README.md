# GastroERP - Restaurant Mini-ERP System

A comprehensive Spring Boot & PostgreSQL based mini-ERP system designed specifically for restaurants, featuring recipe-driven inventory tracking, stock movement auditing, and food cost KPI calculation.

## Features

### 1. Recipe-Driven Inventory Tracking
- Create and manage recipes with multiple ingredients
- Define precise ingredient quantities per recipe
- Track ingredient consumption automatically when recipes are produced
- Monitor stock levels in real-time
- Get alerts for low stock ingredients

### 2. Stock Movement Auditing
- Complete audit trail for all inventory changes
- Track different movement types:
  - **PURCHASE**: Stock increases from suppliers
  - **CONSUMPTION**: Stock decreases from recipe production
  - **ADJUSTMENT**: Manual inventory adjustments
  - **WASTE**: Stock losses due to spoilage/damage
  - **RETURN**: Stock returns to suppliers
- Record who performed each movement and why
- Query movements by date range, ingredient, or type
- Link movements to specific recipes for full traceability

### 3. Food Cost KPI Calculation
- Calculate total recipe costs based on current ingredient prices
- Determine cost per serving
- Calculate gross profit and profit margins
- Track food cost percentage for each recipe
- Compare costs across all recipes
- Support pricing strategy decisions

### 4. Additional Features
- Supplier management
- Unit of measure tracking
- Minimum stock level alerts
- Complete RESTful API
- OpenAPI/Swagger documentation
- PostgreSQL database for reliability

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **API Documentation**: Springdoc OpenAPI (Swagger UI)
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

## Database Setup

1. Install PostgreSQL if not already installed
2. Create a database:
```sql
CREATE DATABASE gastroerp;
```

3. Update `src/main/resources/application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gastroerp
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Installation & Running

1. Clone the repository:
```bash
git clone https://github.com/justin-leitol/gasto-erp.git
cd gasto-erp
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the Swagger UI documentation at:
```
http://localhost:8080/swagger-ui.html
```

## API Endpoints Overview

### Ingredients
- `POST /api/ingredients` - Create ingredient
- `GET /api/ingredients` - List all ingredients
- `GET /api/ingredients/{id}` - Get ingredient details
- `PUT /api/ingredients/{id}` - Update ingredient
- `DELETE /api/ingredients/{id}` - Delete ingredient
- `GET /api/ingredients/low-stock` - Get low stock ingredients
- `GET /api/ingredients/inventory/status` - Get inventory status

### Recipes
- `POST /api/recipes` - Create recipe
- `GET /api/recipes` - List all recipes
- `GET /api/recipes/{id}` - Get recipe details
- `GET /api/recipes/{id}/with-ingredients` - Get recipe with ingredients
- `PUT /api/recipes/{id}` - Update recipe
- `DELETE /api/recipes/{id}` - Delete recipe
- `POST /api/recipes/{id}/ingredients` - Add ingredient to recipe
- `DELETE /api/recipes/{recipeId}/ingredients/{ingredientId}` - Remove ingredient

### Stock Movements
- `POST /api/stock-movements` - Record stock movement
- `GET /api/stock-movements` - List all movements
- `GET /api/stock-movements/ingredient/{id}` - Get movements by ingredient
- `GET /api/stock-movements/type/{type}` - Get movements by type
- `GET /api/stock-movements/date-range` - Get movements in date range
- `POST /api/stock-movements/recipe-production/{id}` - Record recipe production

### Food Cost KPIs
- `GET /api/food-cost/recipe/{id}` - Get recipe cost KPIs
- `GET /api/food-cost/all-recipes` - Get all recipe costs
- `GET /api/food-cost/recipe/{id}/percentage` - Get food cost percentage

### Suppliers
- `POST /api/suppliers` - Create supplier
- `GET /api/suppliers` - List all suppliers
- `GET /api/suppliers/{id}` - Get supplier details
- `PUT /api/suppliers/{id}` - Update supplier
- `DELETE /api/suppliers/{id}` - Delete supplier

## Usage Examples

### 1. Create an Ingredient
```bash
curl -X POST http://localhost:8080/api/ingredients \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Tomatoes",
    "description": "Fresh tomatoes",
    "unitOfMeasure": "kg",
    "unitCost": 2.50,
    "stockQuantity": 50.0,
    "minimumStock": 10.0
  }'
```

### 2. Create a Recipe
```bash
curl -X POST http://localhost:8080/api/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pasta Bolognese",
    "description": "Classic Italian pasta",
    "servings": 4,
    "sellingPrice": 15.00
  }'
```

### 3. Add Ingredient to Recipe
```bash
curl -X POST http://localhost:8080/api/recipes/1/ingredients \
  -H "Content-Type: application/json" \
  -d '{
    "ingredientId": 1,
    "quantity": 0.5
  }'
```

### 4. Record Stock Purchase
```bash
curl -X POST http://localhost:8080/api/stock-movements \
  -H "Content-Type: application/json" \
  -d '{
    "ingredientId": 1,
    "movementType": "PURCHASE",
    "quantity": 20.0,
    "reason": "Weekly supplier delivery",
    "performedBy": "John Doe"
  }'
```

### 5. Calculate Recipe Cost
```bash
curl http://localhost:8080/api/food-cost/recipe/1
```

## Domain Model

### Core Entities

1. **Ingredient**: Base inventory items (flour, tomatoes, etc.)
2. **Recipe**: Menu items/dishes composed of ingredients
3. **RecipeIngredient**: Links recipes to ingredients with quantities
4. **StockMovement**: Audit trail for all inventory changes
5. **Supplier**: Ingredient suppliers

## Business Logic

### Recipe Production Flow
1. Record recipe production via API
2. System automatically calculates ingredient consumption
3. Creates stock movement records for each ingredient
4. Updates inventory levels
5. Maintains complete audit trail

### Cost Calculation
- Total recipe cost = Sum of (ingredient quantity × unit cost)
- Cost per serving = Total cost ÷ number of servings
- Profit margin = (Selling price - Cost per serving) ÷ Selling price × 100

## Development

### Project Structure
```
src/
├── main/
│   ├── java/com/gastro/erp/
│   │   ├── controller/     # REST API controllers
│   │   ├── service/        # Business logic
│   │   ├── repository/     # Data access layer
│   │   ├── model/          # Domain entities
│   │   └── dto/            # Data transfer objects
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/gastro/erp/
```

### Running Tests
```bash
mvn test
```

## Future Enhancements

- User authentication and authorization
- Multi-location support
- Purchase order management
- Inventory forecasting
- Reporting dashboard
- Mobile app integration
- Barcode/QR code scanning
- Integration with POS systems

## License

This project is licensed under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Contact

For questions or support, please open an issue on GitHub.
