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
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL
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

DELETE FROM User;

INSERT INTO User (id, first_name, last_name, user_name, is_mentor)
VALUES ('ba7c6505-19fd-47c3-87a6-c6af6e5322b7', 'Fei', 'Xiu', 'feixiu', 1);

INSERT INTO User (id, first_name, last_name, user_name, is_mentor)
VALUES ('8d6153fc-83e5-4b3a-90ac-d081ff789cef', 'Alan', 'Ly', 'alanly', 0);

DELETE FROM Mentee_Profile;

INSERT INTO Mentee_Profile (id, mentee_id, email)
VALUES ('c5f473b4-3311-40b1-8fb3-f70357894754', '8d6153fc-83e5-4b3a-90ac-d081ff789cef', 'alanly@inno.edu');

DELETE FROM School;

INSERT INTO School (id, name, description)
VALUES ('0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', 'Stanford', 'Stanford is an amazing university.');

INSERT INTO School (id, name, description)
VALUES ('a10afaca-2016-44b8-940b-5b88323901b9', 'Berkeley', 'Berkeley is an outstanding university.');

DELETE FROM Mentor_Profile;

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, status)
VALUES ('0e9e40c0-b44b-4387-92a9-9d75d10e3d42', 'ba7c6505-19fd-47c3-87a6-c6af6e5322b7',
        '0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', 'feixiu@inno.edu', 1);

DELETE FROM Availability;

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('0c55130c-6e63-420b-b3ae-b2485caadc23', '0e9e40c0-b44b-4387-92a9-9d75d10e3d42',
        '2017-11-09 12:00:01.000', '2017-11-10 12:00:01.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('8c0af3b9-2883-40f3-bf85-26ee0f506fde', '0e9e40c0-b44b-4387-92a9-9d75d10e3d42',
        '2017-12-09 12:00:01.000', '2017-12-10 12:00:01.000');

DELETE FROM Appointment;

INSERT INTO Appointment (id, mentor_profile_id, mentee_id, from_date_time, to_date_time, status)
VALUES ('f192270f-2dad-4bcd-96c3-c3765df77ce8', '0e9e40c0-b44b-4387-92a9-9d75d10e3d42',
        '8d6153fc-83e5-4b3a-90ac-d081ff789cef', '2017-11-10 09:00:01.000',
        '2017-11-10 10:00:01.000', 0);

INSERT INTO Appointment (id, mentor_profile_id, mentee_id, from_date_time, to_date_time, status)
VALUES ('42f2431a-0216-416e-b795-29292b637ec4', '0e9e40c0-b44b-4387-92a9-9d75d10e3d42',
        '8d6153fc-83e5-4b3a-90ac-d081ff789cef', '2017-11-10 10:00:01.000',
        '2017-11-10 11:00:01.000', 1);
