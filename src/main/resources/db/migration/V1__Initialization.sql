CREATE TABLE User (
  id         UUID PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  user_name  VARCHAR(30)  NOT NULL,
  is_mentor  BOOLEAN      NOT NULL
);

CREATE TABLE Mentee_Profile (
  id        UUID PRIMARY KEY,
  mentee_id UUID         NOT NULL,
  email     VARCHAR(255) NOT NULL,

  FOREIGN KEY (mentee_id)
  REFERENCES User (id)
  ON DELETE CASCADE,
);

CREATE TABLE School (
  id   UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE Mentor_Profile (
  id            UUID PRIMARY KEY,
  mentor_id     UUID         NOT NULL,
  school_id UUID         NOT NULL,
  email         VARCHAR(255) NOT NULL,
  status        SMALLINT     NOT NULL,

  FOREIGN KEY (mentor_id)
  REFERENCES User (id)
  ON DELETE CASCADE,

  FOREIGN KEY (school_id)
  REFERENCES School (id)
  ON DELETE CASCADE,
);


CREATE TABLE Availability (
  id                UUID PRIMARY KEY,
  mentor_profile_id UUID      NOT NULL,
  from_date_time    TIMESTAMP NOT NULL,
  to_date_time      TIMESTAMP NOT NULL,

  FOREIGN KEY (mentor_profile_id)
  REFERENCES Mentor_Profile (id)
  ON DELETE CASCADE,
);

CREATE TABLE Appointment (
  id                UUID PRIMARY KEY,
  mentor_profile_id UUID      NOT NULL,
  mentee_id         UUID      NOT NULL,
  from_date_time    TIMESTAMP NOT NULL,
  to_date_time      TIMESTAMP NOT NULL,
  status            SMALLINT  NOT NULL,

  FOREIGN KEY (mentor_profile_id)
  REFERENCES Mentor_Profile (id)
  ON DELETE CASCADE,

  FOREIGN KEY (mentee_id)
  REFERENCES User (id)
  ON DELETE CASCADE,
);

-- Temporary stub data