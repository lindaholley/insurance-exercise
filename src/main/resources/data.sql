DROP TABLE IF EXISTS customer_item;

CREATE TABLE customer_item (
  customer_item_id INT AUTO_INCREMENT  PRIMARY KEY,
  customer_id INT,
  item_id VARCHAR(250) NOT NULL,
  access_token VARCHAR(250) NOT NULL,
  account_id VARCHAR DEFAULT NULL,
  item_status INT NOT NULL
);

INSERT INTO customer_item (customer_id, item_id, access_token, account_id, item_status) VALUES
  (1, 'test_item', 'test_access_token', 'test_account_id', 0);