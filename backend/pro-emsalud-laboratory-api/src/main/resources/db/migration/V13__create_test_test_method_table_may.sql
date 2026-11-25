CREATE TABLE test_test_method (
  test_id BIGINT NOT NULL,
  test_method VARCHAR(150) NOT NULL,
  PRIMARY KEY (test_id, test_method),
  CONSTRAINT fk_ttm_test FOREIGN KEY (test_id)
    REFERENCES test(id) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO test_test_method (test_id, test_method)
SELECT id, test_method FROM test WHERE test_method IS NOT NULL;

ALTER TABLE test DROP COLUMN test_method;