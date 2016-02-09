DROP TABLE address IF EXISTS;
DROP TABLE user IF EXISTS;

CREATE TABLE user  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    created_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE address (
  id BIGINT IDENTITY NOT NULL PRIMARY KEY,
  address1 VARCHAR(100),
  address2 VARCHAR(100),
  city VARCHAR(30),
  country VARCHAR(30),
  state VARCHAR(2),
  zip_code INTEGER,
  user_id  BIGINT,
  created_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user(id)
);