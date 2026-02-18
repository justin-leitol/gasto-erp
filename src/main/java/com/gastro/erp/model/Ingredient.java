package com.gastro.erp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ingredient name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @NotBlank(message = "Unit of measure is required")
    @Column(nullable = false)
    private String unitOfMeasure; // e.g., kg, liters, pieces

    @NotNull(message = "Unit cost is required")
    @Positive(message = "Unit cost must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost;

    @NotNull(message = "Stock quantity is required")
    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal stockQuantity = BigDecimal.ZERO;

    @Column(precision = 10, scale = 3)
    private BigDecimal minimumStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
