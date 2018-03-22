CREATE TABLE users (
  id     INT AUTO_INCREMENT PRIMARY KEY,
  name   TEXT NOT NULL,
  active BOOL NOT NULL
)
  ENGINE = InnoDB;
CREATE TABLE posts (
  id       INT AUTO_INCREMENT PRIMARY KEY,
  title    TEXT NOT NULL,
  contents TEXT NOT NULL,
  user_id  INT  NOT NULL,
  reply_to INT  NOT NULL
)
  ENGINE = InnoDB;