package com.gastro.erp.exception;

public class InvalidMovementTypeException extends RuntimeException {
    public InvalidMovementTypeException(String providedType, String[] validTypes) {
        super(String.format("Invalid movement type: '%s'. Valid types are: %s", 
            providedType, String.join(", ", validTypes)));
    }
}
