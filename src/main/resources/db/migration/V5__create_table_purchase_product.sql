CREATE TABLE purchase_product (
  id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  purchase_id BIGINT(20) NOT NULL,
  product_id BIGINT(20) NOT NULL,
  quantity BIGINT NOT NULL,
  FOREIGN KEY (purchase_id) REFERENCES purchase (id),
  FOREIGN KEY (product_id) REFERENCES product (id)
);