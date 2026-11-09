
CREATE TABLE urinalysis (
    id BIGINT NOT NULL AUTO_INCREMENT,

    -- =========================
    -- EXAMEN FISICO
    -- =========================

    color VARCHAR(255) NOT NULL,
    appearance VARCHAR(255) NOT NULL,
    specific_gravity VARCHAR(255) NOT NULL,

    -- =========================
    -- EXAMEN QUIMICO
    -- =========================

    pH VARCHAR(255) NOT NULL,
    leukocytes VARCHAR(255) NOT NULL,
    nitrites VARCHAR(255) NOT NULL,
    urobilinogen VARCHAR(255) NOT NULL,
    protein VARCHAR(255) NOT NULL,
    glucose VARCHAR(255) NOT NULL,
    ketones VARCHAR(255) NOT NULL,
    bilirubin VARCHAR(255) NOT NULL,
    erythrocytes VARCHAR(255) NOT NULL,

    -- =========================
    -- EXAMEN MICROSCOPICO
    -- =========================

    microscopic_leukocytes VARCHAR(255) NOT NULL,
    microscopic_erythrocytes VARCHAR(255) NOT NULL,
    epithelial_cells VARCHAR(255) NOT NULL,
    bacteria VARCHAR(255) NOT NULL,
    mucus VARCHAR(255) NOT NULL,
    crystals VARCHAR(255) NOT NULL,
    yeast VARCHAR(255) NOT NULL,
    others VARCHAR(255) NULL,

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

    KEY idx_urinalysis_order (order_id),
    KEY idx_urinalysis_test (test_id),
    KEY idx_urinalysis_created_at (created_at),

    CONSTRAINT fk_urinalysis_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id),

    CONSTRAINT fk_urinalysis_test
        FOREIGN KEY (test_id)
        REFERENCES test(id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;
