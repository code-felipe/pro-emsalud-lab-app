
CREATE TABLE torchs (
    id BIGINT NOT NULL AUTO_INCREMENT,

    unit_price DECIMAL(19, 2),

    -- TorchIgG
    toxoplasma_igg VARCHAR(255),
    cytomegalovirus_igg VARCHAR(255),
    rubella_igg VARCHAR(255),
    herpes_igg VARCHAR(255),

    -- TorchIgM
    toxoplasma_igm VARCHAR(255),
    cytomegalovirus_igm VARCHAR(255),
    rubella_igm VARCHAR(255),
    herpes_igm VARCHAR(255),

    order_id BIGINT NOT NULL,
    test_id BIGINT NOT NULL,

    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),

    PRIMARY KEY (id),

    CONSTRAINT fk_torchs_order
        FOREIGN KEY (order_id) REFERENCES orders(id),

    CONSTRAINT fk_torchs_test
        FOREIGN KEY (test_id) REFERENCES test(id),

    INDEX idx_torchs_order_id (order_id),
    INDEX idx_torchs_test_id (test_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
