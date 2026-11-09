ALTER TABLE test
DROP COLUMN test_group;

CREATE TABLE widal_reaction (
    id BIGINT NOT NULL AUTO_INCREMENT,
    unit_price DECIMAL(38,2) NULL,

    salmonella_typhi_o_1_20 BIT(1) NOT NULL,
    salmonella_typhi_o_1_40 BIT(1) NOT NULL,
    salmonella_typhi_o_1_80 BIT(1) NOT NULL,
    salmonella_typhi_o_1_160 BIT(1) NOT NULL,
    salmonella_typhi_o_1_320 BIT(1) NOT NULL,

    salmonella_typhi_h_1_20 BIT(1) NOT NULL,
    salmonella_typhi_h_1_40 BIT(1) NOT NULL,
    salmonella_typhi_h_1_80 BIT(1) NOT NULL,
    salmonella_typhi_h_1_160 BIT(1) NOT NULL,
    salmonella_typhi_h_1_320 BIT(1) NOT NULL,

    salmonella_typhi_o_result BIT(1) NOT NULL,
    salmonella_typhi_h_result BIT(1) NOT NULL,

    salmonella_typhi_o_result_titulation VARCHAR(255) NOT NULL,
    salmonella_typhi_h_result_titulation VARCHAR(255) NOT NULL,

    observation VARCHAR(70) NULL,

    order_id BIGINT NOT NULL,
    test_id BIGINT NOT NULL,

    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,

    PRIMARY KEY (id),

    KEY idx_widal_reaction_order (order_id),
    KEY idx_widal_reaction_test (test_id),

    CONSTRAINT fk_widal_reaction_order
        FOREIGN KEY (order_id) REFERENCES orders (id),

    CONSTRAINT fk_widal_reaction_test
        FOREIGN KEY (test_id) REFERENCES test (id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;