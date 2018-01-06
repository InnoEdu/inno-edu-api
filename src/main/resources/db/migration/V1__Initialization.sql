CREATE TABLE User (
  id         BINARY(16) PRIMARY KEY,
  first_name VARCHAR(50)  NOT NULL,
  last_name  VARCHAR(50)  NOT NULL,
  username   VARCHAR(30)  NOT NULL,
  email      VARCHAR(255) NOT NULL,
  password   VARCHAR(30)  NOT NULL
);

CREATE TABLE School (
  id          BINARY(16) PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  description TEXT         NOT NULL
);

CREATE TABLE Profile (
  id          BINARY(16) PRIMARY KEY,
  user_id     BINARY(16)     NOT NULL,
  school_id   BINARY(16)     NULL,
  description TEXT           NOT NULL,
  rate        DECIMAL(15, 2) NULL,
  location    VARCHAR(255)   NOT NULL,
  company     VARCHAR(100)   NULL,

  FOREIGN KEY (user_id)
  REFERENCES User (id)
    ON DELETE CASCADE,

  FOREIGN KEY (school_id)
  REFERENCES School (id)
    ON DELETE CASCADE
);

CREATE TABLE Profile_Association (
  id          BINARY(16) PRIMARY KEY,
  profile_id  BINARY(16),
  school_id   BINARY(16)   NOT NULL,
  status      SMALLINT     NOT NULL,
  description VARCHAR(255) NULL,

  FOREIGN KEY (profile_id)
  REFERENCES Profile (id)
    ON DELETE CASCADE,

  FOREIGN KEY (school_id)
  REFERENCES School (id)
    ON DELETE CASCADE
);

CREATE TABLE Experience (
  id          BINARY(16) PRIMARY KEY,
  profile_id  BINARY(16)   NOT NULL,
  title       VARCHAR(100) NOT NULL,
  institution VARCHAR(100) NOT NULL,
  location    VARCHAR(255) NOT NULL,
  area        VARCHAR(100) NULL,
  from_date   DATE         NULL,
  to_date     DATE         NULL,
  description TEXT         NULL,
  type        SMALLINT     NOT NULL,

  FOREIGN KEY (profile_id)
  REFERENCES Profile (id)
    ON DELETE CASCADE
);

CREATE TABLE Interest (
  id          BINARY(16) PRIMARY KEY,
  profile_id  BINARY(16)   NOT NULL,
  title       VARCHAR(100) NOT NULL,
  description TEXT         NOT NULL,

  FOREIGN KEY (profile_id)
  REFERENCES Profile (id)
    ON DELETE CASCADE
);

CREATE TABLE Accomplishment (
  id          BINARY(16) PRIMARY KEY,
  profile_id  BINARY(16)   NOT NULL,
  title       VARCHAR(100) NOT NULL,
  description TEXT         NOT NULL,
  type        SMALLINT     NOT NULL,

  FOREIGN KEY (profile_id)
  REFERENCES Profile (id)
    ON DELETE CASCADE
);


CREATE TABLE Availability (
  id                BINARY(16) PRIMARY KEY,
  mentor_profile_id BINARY(16) NOT NULL,
  from_date_time    TIMESTAMP  NOT NULL,
  to_date_time      TIMESTAMP  NOT NULL,

  FOREIGN KEY (mentor_profile_id)
  REFERENCES Profile (id)
    ON DELETE CASCADE
);

CREATE TABLE Appointment (
  id                BINARY(16) PRIMARY KEY,
  mentor_profile_id BINARY(16)     NOT NULL,
  mentee_profile_id BINARY(16)     NOT NULL,
  from_date_time    TIMESTAMP      NOT NULL,
  to_date_time      TIMESTAMP      NOT NULL,
  description       TEXT           NOT NULL,
  status            SMALLINT       NOT NULL,
  fee               DECIMAL(15, 2) NOT NULL,
  reason            VARCHAR(255),

  FOREIGN KEY (mentor_profile_id)
  REFERENCES Profile (id)
    ON DELETE CASCADE,

  FOREIGN KEY (mentee_profile_id)
  REFERENCES Profile (id)
    ON DELETE CASCADE
);

CREATE TABLE Feedback (
  id             BINARY(16) PRIMARY KEY,
  appointment_id BINARY(16)   NOT NULL,
  source         SMALLINT     NOT NULL,
  rating         SMALLINT     NOT NULL,
  description    VARCHAR(255) NULL,
  FOREIGN KEY (appointment_id)
  REFERENCES Appointment (id)
    ON DELETE CASCADE
);

-- Stub data

-- Mentors

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES (${map}('ba7c650519fd47c387a6c6af6e5322b7'), 'Fei', 'Xiu', 'feixiu', 'feixiu@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES (${map}('8d6153fc83e54b3a90acd081ff789cef'), 'Alan', 'Ly', 'alanly', 'alanly@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES
  (${map}('df54ff863caa4145b228284f5d4a908a'), 'Gustavo', 'Di Domenico', 'gdomenico', 'gustavo@inno.edu', 'password');

-- Mentees

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES (${map}('e3495a43a0af42b7ab91a3801b1b56ab'), 'Tuany', 'Di Domenico', 'tuany', 'tuany@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES (${map}('c5e6b39233e14255a249f777b6ab355d'), 'Elisete', 'Muller', 'elisete', 'eluisete@inno.edu', 'password');

-- Schools

INSERT INTO School (id, name, description)
VALUES (${map}('0a58153cc15f4e5b802cbbf5d6c1c55c'), 'Stanford', 'Stanford is an amazing university.');

INSERT INTO School (id, name, description)
VALUES (${map}('a10afaca201644b8940b5b88323901b9'), 'Berkeley', 'Berkeley is an outstanding university.');

INSERT INTO School (id, name, description)
VALUES (${map}('7f297cd9723b43c98021a8530129dedb'), 'PUCRS', 'PUCRS is an outstanding university.');

INSERT INTO School (id, name, description)
VALUES (${map}('83768bb035514586bbba03efb5f8d7bd'), 'Massachusetts Institute of Technology',
        'MIT is an outstanding university.');

-- Profiles

INSERT INTO Profile (id, user_id, school_id, description, rate, location)
VALUES (${map}('0e9e40c0b44b438792a99d75d10e3d42'), ${map}('ba7c650519fd47c387a6c6af6e5322b7'),
        ${map}('0a58153cc15f4e5b802cbbf5d6c1c55c'), 'Fei is a great mentor.', 5.0, 'San Francisco, CA');

INSERT INTO Profile (id, user_id, school_id, description, rate, location)
VALUES (${map}('e1b66612a94a4db386a104f3a102227b'), ${map}('df54ff863caa4145b228284f5d4a908a'),
        ${map}('7f297cd9723b43c98021a8530129dedb'), 'Gustavo is a great mentor.', 10.0, 'San Francisco, CA');

INSERT INTO Profile (id, user_id, school_id, description, rate, location)
VALUES (${map}('2744d1cbb25e4a61879dad3d15ffebe2'), ${map}('8d6153fc83e54b3a90acd081ff789cef'),
        ${map}('a10afaca201644b8940b5b88323901b9'), 'Alan is a great mentor.', 15.0, 'San Francisco, CA');

INSERT INTO Profile (id, user_id, description, location)
VALUES (${map}('c5f473b4331140b18fb3f70357894754'), ${map}('e3495a43a0af42b7ab91a3801b1b56ab'),
        'Tuany is a great mentee.', 'San Francisco, CA');

INSERT INTO Profile (id, user_id, description, location)
VALUES (${map}('71b31ec9207d4d469e33c4b4024db0ed'), ${map}('c5e6b39233e14255a249f777b6ab355d'),
        'Eluisete is a great mentee.', 'San Francisco, CA');

-- Availability

-- Fei

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc24'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        '2018-01-01 18:00:00.000', '2018-01-01 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc25'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        '2018-01-02 18:00:00.000', '2018-01-02 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc26'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        '2018-01-03 18:00:00.000', '2018-01-03 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc27'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        '2018-01-04 18:00:00.000', '2018-01-04 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc28'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        '2018-01-05 18:00:00.000', '2018-01-05 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc29'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        '2018-01-06 18:00:00.000', '2018-01-06 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc10'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        '2018-01-07 18:00:00.000', '2018-01-07 23:00:00.000');

-- Alan

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc11'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        '2018-01-01 13:00:00.000', '2018-01-01 18:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc12'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        '2018-01-02 13:00:00.000', '2018-01-02 18:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc13'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        '2018-01-03 13:00:00.000', '2018-01-03 18:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc14'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        '2018-01-04 13:00:00.000', '2018-01-04 18:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc15'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        '2018-01-05 13:00:00.000', '2018-01-05 18:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc16'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        '2018-01-06 13:00:00.000', '2018-01-06 18:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc17'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        '2018-01-07 13:00:00.000', '2018-01-07 18:00:00.000');

-- Gustavo

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc31'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        '2018-01-01 13:00:00.000', '2018-01-01 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc32'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        '2018-01-02 13:00:00.000', '2018-01-02 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc33'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        '2018-01-03 13:00:00.000', '2018-01-03 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc34'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        '2018-01-04 13:00:00.000', '2018-01-04 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc35'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        '2018-01-05 13:00:00.000', '2018-01-05 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc36'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        '2018-01-06 13:00:00.000', '2018-01-06 23:00:00.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES (${map}('0c55130c6e63420bb3aeb2485caadc37'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        '2018-01-07 13:00:00.000', '2018-01-07 23:00:00.000');

-- Appointments

-- Tuany

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES (${map}('f192270f2dad4bcd96c3c3765df77ce1'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        ${map}('c5f473b4331140b18fb3f70357894754'),
        '2018-01-02 18:00:00.000',
        '2018-01-02 19:00:00.000', 'My great first appointment.',
        1, 5);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES (${map}('f192270f2dad4bcd96c3c3765df77ce2'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        ${map}('c5f473b4331140b18fb3f70357894754'),
        '2018-01-02 13:00:00.000',
        '2018-01-02 14:00:00.000', 'My great second appointment.',
        1, 15);

-- Elisete

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES (${map}('f192270f2dad4bcd96c3c3765df77ce3'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        ${map}('71b31ec9207d4d469e33c4b4024db0ed'), '2018-01-03 14:00:00.000',
        '2018-01-03 15:00:00.000', 'My great first appointment.', 1, 10);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES (${map}('f192270f2dad4bcd96c3c3765df77ce4'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        ${map}('71b31ec9207d4d469e33c4b4024db0ed'), '2018-01-03 15:00:00.000',
        '2018-01-03 16:00:00.000', 'My great second appointment.', 1, 15);

-- Feedbacks

INSERT INTO Feedback (id, appointment_id, source, rating, description)
VALUES (${map}('bd06f884b1264be8b637758519dea5a5'), ${map}('f192270f2dad4bcd96c3c3765df77ce3'), 0, 5, 'Great session');
