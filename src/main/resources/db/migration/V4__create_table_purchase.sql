CREATE TABLE purchase (
  id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT(20) NOT NULL,
  total DECIMAL NOT NULL,
  createAt DATETIME NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (id)
  );