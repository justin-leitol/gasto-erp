CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    unit VARCHAR(30) NOT NULL,
    minimum_stock NUMERIC(12, 3) NOT NULL DEFAULT 0,
    purchase_price_per_unit NUMERIC(12, 4) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE supplier (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    contact_email VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stock_movement (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    movement_type VARCHAR(30) NOT NULL,
    quantity NUMERIC(12, 3) NOT NULL,
    reference_id VARCHAR(100),
    note VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_stock_movement_product
      FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE INDEX idx_stock_movement_product_created_at
    ON stock_movement (product_id, created_at);
