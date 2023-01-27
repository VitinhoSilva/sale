CREATE TABLE stock (
  id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  product_id BIGINT(20) NOT NULL,
  availableQuantity BIGINT NOT NULL,
  FOREIGN KEY (product_id) REFERENCES product (id)
);