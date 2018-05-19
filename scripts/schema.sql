CREATE TABLE users (
  id     INT AUTO_INCREMENT PRIMARY KEY,
  name   TEXT NOT NULL,
  active BOOL NOT NULL,
  gender TEXT NOT NULL,
  registered_at TIMESTAMP NOT NULL
) ENGINE = InnoDB;

CREATE TABLE posts (
  id       INT AUTO_INCREMENT PRIMARY KEY,
  title    TEXT NOT NULL,
  contents TEXT NOT NULL,
  user_id  INT  NOT NULL,
  reply_to INT  NOT NULL,
  like_count INT  NOT NULL
) ENGINE = InnoDB;

CREATE TABLE points (
  id       INT AUTO_INCREMENT PRIMARY KEY,
  user_id  INT  NOT NULL,
  amount   DECIMAL(10, 3)  NOT NULL,
  occurred_at TIMESTAMP NOT NULL
) ENGINE = InnoDB;