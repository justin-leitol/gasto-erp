package com.gastro.erp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id", nullable = false)
    @NotNull(message = "Ingredient is required")
    private Ingredient ingredient;

    @NotNull(message = "Movement type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    @NotNull(message = "Quantity is required")
    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;

    @Column(precision = 10, scale = 3)
    private BigDecimal previousStock;

    @Column(precision = 10, scale = 3)
    private BigDecimal newStock;

    @NotBlank(message = "Reason is required")
    @Column(nullable = false, length = 500)
    private String reason;

    @Column(length = 1000)
    private String notes;

    private String performedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe relatedRecipe;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum MovementType {
        PURCHASE,      // Stock increase from supplier
        CONSUMPTION,   // Stock decrease from recipe production
        ADJUSTMENT,    // Manual stock adjustment (inventory count)
        WASTE,         // Stock loss due to spoilage/damage
        RETURN         // Stock return to supplier
    }
}
