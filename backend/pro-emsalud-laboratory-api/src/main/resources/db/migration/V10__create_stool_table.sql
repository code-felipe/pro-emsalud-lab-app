
CREATE TABLE stools (
    id BIGINT NOT NULL AUTO_INCREMENT,

    -- =========================
    -- EXAMEN MACROSCOPICO
    -- =========================

    color VARCHAR(255) NOT NULL,
    consistency VARCHAR(255) NOT NULL,
    mucus VARCHAR(255) NOT NULL,
    pH VARCHAR(255) NOT NULL,
    blood VARCHAR(255) NOT NULL,
    food_remains VARCHAR(255) NOT NULL,

    -- =========================
    -- EXAMEN MICROSCOPICO
    -- =========================

    leukocytes VARCHAR(255) NOT NULL,
    erythrocytes VARCHAR(255) NOT NULL,
    yeast VARCHAR(255) NOT NULL,
    vegetable_cells VARCHAR(255) NOT NULL,
    muscle_fibers VARCHAR(255) NOT NULL,
    fats VARCHAR(255) NOT NULL,
    starches VARCHAR(255) NOT NULL,
    parasites VARCHAR(255) NOT NULL,
    bacteria VARCHAR(255) NOT NULL,

    -- =========================
    -- OBSERVACIONES
    -- =========================

    observation TEXT NULL,

    -- =========================
    -- INFORMACION GENERAL
    -- =========================

    unit_price DECIMAL(10,2) NULL,

    order_id BIGINT NOT NULL,
    test_id BIGINT NOT NULL,

    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,

    PRIMARY KEY (id),

    -- =========================
    -- INDEXES
    -- =========================

    KEY idx_stool_order (order_id),
    KEY idx_stool_test (test_id),
    KEY idx_stool_created_at (created_at),

    -- =========================
    -- FOREIGN KEYS
    -- =========================

    CONSTRAINT fk_stool_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id),

    CONSTRAINT fk_stool_test
        FOREIGN KEY (test_id)
        REFERENCES test(id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;
