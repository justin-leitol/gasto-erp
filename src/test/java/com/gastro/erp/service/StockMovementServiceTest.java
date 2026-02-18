package com.gastro.erp.service;

import com.gastro.erp.dto.StockMovementRequest;
import com.gastro.erp.model.Ingredient;
import com.gastro.erp.model.StockMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StockMovementServiceTest {

    @Autowired
    private StockMovementService stockMovementService;

    @Autowired
    private IngredientService ingredientService;

    private Ingredient testIngredient;

    @BeforeEach
    public void setUp() {
        testIngredient = new Ingredient();
        testIngredient.setName("Test Ingredient");
        testIngredient.setUnitOfMeasure("kg");
        testIngredient.setUnitCost(new BigDecimal("5.00"));
        testIngredient.setStockQuantity(new BigDecimal("100"));
        testIngredient.setMinimumStock(new BigDecimal("20"));
        testIngredient = ingredientService.createIngredient(testIngredient);
    }

    @Test
    public void testRecordPurchaseMovement() {
        StockMovementRequest request = new StockMovementRequest();
        request.setIngredientId(testIngredient.getId());
        request.setMovementType("PURCHASE");
        request.setQuantity(new BigDecimal("50"));
        request.setReason("Weekly delivery");
        request.setPerformedBy("Test User");

        StockMovement movement = stockMovementService.recordStockMovement(request);

        assertNotNull(movement);
        assertEquals(testIngredient.getId(), movement.getIngredient().getId());
        assertEquals(StockMovement.MovementType.PURCHASE, movement.getMovementType());
        assertEquals(0, new BigDecimal("50").compareTo(movement.getQuantity()));
        assertEquals(0, new BigDecimal("100").compareTo(movement.getPreviousStock()));
        assertEquals(0, new BigDecimal("150").compareTo(movement.getNewStock()));
    }

    @Test
    public void testRecordConsumptionMovement() {
        StockMovementRequest request = new StockMovementRequest();
        request.setIngredientId(testIngredient.getId());
        request.setMovementType("CONSUMPTION");
        request.setQuantity(new BigDecimal("30"));
        request.setReason("Recipe production");
        request.setPerformedBy("Test Chef");

        StockMovement movement = stockMovementService.recordStockMovement(request);

        assertNotNull(movement);
        assertEquals(StockMovement.MovementType.CONSUMPTION, movement.getMovementType());
        assertEquals(0, new BigDecimal("100").compareTo(movement.getPreviousStock()));
        assertEquals(0, new BigDecimal("70").compareTo(movement.getNewStock()));
    }

    @Test
    public void testGetMovementsByIngredient() {
        // Record multiple movements
        StockMovementRequest request1 = new StockMovementRequest();
        request1.setIngredientId(testIngredient.getId());
        request1.setMovementType("PURCHASE");
        request1.setQuantity(new BigDecimal("20"));
        request1.setReason("Test purchase");
        request1.setPerformedBy("User 1");
        stockMovementService.recordStockMovement(request1);

        StockMovementRequest request2 = new StockMovementRequest();
        request2.setIngredientId(testIngredient.getId());
        request2.setMovementType("CONSUMPTION");
        request2.setQuantity(new BigDecimal("10"));
        request2.setReason("Test consumption");
        request2.setPerformedBy("User 2");
        stockMovementService.recordStockMovement(request2);

        List<StockMovement> movements = stockMovementService.getMovementsByIngredient(testIngredient.getId());

        assertNotNull(movements);
        assertEquals(2, movements.size());
    }

    @Test
    public void testGetMovementsByType() {
        StockMovementRequest request = new StockMovementRequest();
        request.setIngredientId(testIngredient.getId());
        request.setMovementType("WASTE");
        request.setQuantity(new BigDecimal("5"));
        request.setReason("Spoilage");
        request.setPerformedBy("Manager");
        stockMovementService.recordStockMovement(request);

        List<StockMovement> movements = stockMovementService.getMovementsByType(StockMovement.MovementType.WASTE);

        assertNotNull(movements);
        assertTrue(movements.size() >= 1);
        assertTrue(movements.stream().anyMatch(m -> m.getReason().equals("Spoilage")));
    }
}
