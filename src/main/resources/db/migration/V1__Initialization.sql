CREATE TABLE User (
  id         UUID PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL
);

CREATE TABLE University (
  id   UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE Availability (
  id             UUID PRIMARY KEY,
  user_id        UUID,
  university_id  UUID,
  from_date_time TIMESTAMP,
  to_date_time   TIMESTAMP,

  FOREIGN KEY (user_id)
  REFERENCES User (id)
  ON DELETE CASCADE,

  FOREIGN KEY (university_id)
  REFERENCES University (id)
  ON DELETE CASCADE
);