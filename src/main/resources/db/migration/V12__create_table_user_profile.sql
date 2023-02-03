CREATE TABLE user_profile (
  user_id BIGINT(20) NOT NULL,
  profile VARCHAR(50) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (id)
);