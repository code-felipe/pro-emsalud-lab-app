
CREATE TABLE parameters (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    exam_type VARCHAR(50) NOT NULL,
    input_type VARCHAR(50) NOT NULL,
    display_order INT NULL,
   
    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,

    PRIMARY KEY (id),

    KEY idx_parameter_exam_type (exam_type)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE parameter_options (
    id BIGINT NOT NULL AUTO_INCREMENT,
    value VARCHAR(255) NOT NULL,
    display_order INT NULL,
   
    parameter_id BIGINT NOT NULL,

    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,

    PRIMARY KEY (id),

    KEY idx_parameter_options_parameter (parameter_id),

    CONSTRAINT fk_parameter_options_parameter
        FOREIGN KEY (parameter_id) REFERENCES parameters (id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;
