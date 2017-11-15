CREATE TABLE User (
  id         BINARY(16) PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  user_name  VARCHAR(30)  NOT NULL,
  is_mentor  BOOLEAN      NOT NULL
);

CREATE TABLE Mentee_Profile (
  id          BINARY(16) PRIMARY KEY,
  mentee_id   BINARY(16)   NOT NULL,
  email       VARCHAR(255) NOT NULL,
  description TEXT         NOT NULL,

  FOREIGN KEY (mentee_id)
  REFERENCES User (id)
  ON DELETE CASCADE
);

CREATE TABLE School (
  id          BINARY(16) PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  description TEXT         NOT NULL
);

CREATE TABLE Mentor_Profile (
  id          BINARY(16) PRIMARY KEY,
  mentor_id   BINARY(16)   NOT NULL,
  school_id   BINARY(16)   NOT NULL,
  email       VARCHAR(255) NOT NULL,
  description TEXT         NOT NULL,
  status      SMALLINT     NOT NULL,

  FOREIGN KEY (mentor_id)
  REFERENCES User (id)
  ON DELETE CASCADE,

  FOREIGN KEY (school_id)
  REFERENCES School (id)
  ON DELETE CASCADE
);


CREATE TABLE Availability (
  id                BINARY(16) PRIMARY KEY,
  mentor_profile_id BINARY(16) NOT NULL,
  from_date_time    TIMESTAMP  NOT NULL,
  to_date_time      TIMESTAMP  NOT NULL,

  FOREIGN KEY (mentor_profile_id)
  REFERENCES Mentor_Profile (id)
  ON DELETE CASCADE
);

CREATE TABLE Appointment (
  id                BINARY(16) PRIMARY KEY,
  mentor_profile_id BINARY(16) NOT NULL,
  mentee_profile_id BINARY(16) NOT NULL,
  from_date_time    TIMESTAMP  NOT NULL,
  to_date_time      TIMESTAMP  NOT NULL,
  description       TEXT       NOT NULL,
  status            SMALLINT   NOT NULL,

  FOREIGN KEY (mentor_profile_id)
  REFERENCES Mentor_Profile (id)
  ON DELETE CASCADE,

  FOREIGN KEY (mentee_profile_id)
  REFERENCES Mentee_Profile (id)
  ON DELETE CASCADE
);