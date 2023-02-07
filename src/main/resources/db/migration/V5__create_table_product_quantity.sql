CREATE TABLE product_quantity (
  uuid VARCHAR(36) NOT NULL PRIMARY KEY,
  purchase_id VARCHAR(36) NOT NULL,
  product_id VARCHAR(36) NOT NULL,
  quantity BIGINT NOT NULL,
  FOREIGN KEY (purchase_id) REFERENCES purchase (uuid),
  FOREIGN KEY (product_id) REFERENCES product (uuid)
);