CREATE TABLE simple_chemistry (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    result      TEXT NULL,
    unit_price  DECIMAL(38,2) NULL,
    order_id    BIGINT NOT NULL,
    test_id     BIGINT NOT NULL,
    created_at  DATETIME(6) NULL,
    updated_at  DATETIME(6) NULL,
    PRIMARY KEY (id),
    KEY idx_simple_chemistry_order (order_id),
    KEY idx_simple_chemistry_test (test_id),
    CONSTRAINT fk_simple_chemistry_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_simple_chemistry_test FOREIGN KEY (test_id) REFERENCES test (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;