CREATE TABLE User (
  id         BINARY(16) PRIMARY KEY,
  first_name VARCHAR(50)  NOT NULL,
  last_name  VARCHAR(50)  NOT NULL,
  username   VARCHAR(30)  NOT NULL,
  email      VARCHAR(255) NOT NULL,
  password   VARCHAR(30)  NOT NULL,
  photo_url  VARCHAR(255),
  is_mentor  BOOLEAN      NOT NULL
);

CREATE TABLE Mentee_Profile (
  id          BINARY(16) PRIMARY KEY,
  mentee_id   BINARY(16) NOT NULL,
  description TEXT       NOT NULL,

  FOREIGN KEY (mentee_id)
  REFERENCES User (id)
    ON DELETE CASCADE
);

CREATE TABLE School (
  id          BINARY(16) PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  description TEXT         NOT NULL,
  photo_url   VARCHAR(255)
);

CREATE TABLE Mentor_Profile (
  id          BINARY(16) PRIMARY KEY,
  mentor_id   BINARY(16)     NOT NULL,
  school_id   BINARY(16)     NOT NULL,
  email       VARCHAR(255)   NOT NULL,
  description TEXT           NOT NULL,
  status      SMALLINT       NOT NULL,
  rate        DECIMAL(15, 2) NOT NULL DEFAULT 10,

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
  reason            VARCHAR(255),

  FOREIGN KEY (mentor_profile_id)
  REFERENCES Mentor_Profile (id)
    ON DELETE CASCADE,

  FOREIGN KEY (mentee_profile_id)
  REFERENCES Mentee_Profile (id)
    ON DELETE CASCADE
);

-- Stub data

-- Mentors

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES (${map}('ba7c650519fd47c387a6c6af6e5322b7'), 'Fei', 'Xiu', 'feixiu', 'feixiu@inno.edu', 'password', 1,
        'https://i1.rgstatic.net/ii/profile.image/AS%3A278674336174081%401443452547142_xl/Peng_Fei_Xu.png');

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES (${map}('8d6153fc83e54b3a90acd081ff789cef'), 'Alan', 'Ly', 'alanly', 'alanly@inno.edu', 'password', 1,
        'https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAeBAAAAJDk1ZDQyNzE0LWY2MGQtNDFmYS05MmRmLTRhMzc0MTAxMGEwMQ.jpg');

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES
  (${map}('df54ff863caa4145b228284f5d4a908a'), 'Gustavo', 'Di Domenico', 'gdomenico', 'gustavo@inno.edu', 'password', 1,
   'https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAANPAAAAJGMwMjU0ZWJlLTBjZmEtNDNiNC1hZWVlLTQzNDRjZDM1MDZjZQ.jpg');

-- Mentees

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES (${map}('e3495a43a0af42b7ab91a3801b1b56ab'), 'Tuany', 'Di Domenico', 'tuany', 'tuany@inno.edu', 'password', 0,
        'https://scontent-sea1-1.cdninstagram.com/t51.2885-15/s480x480/e15/c0.80.640.640/13129262_634106980078940_906222652_n.jpg?ig_cache_key=MTI0MDAxMzMyNzYzMDI1ODQyOQ%3D%3D.2.c');

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES (${map}('c5e6b39233e14255a249f777b6ab355d'), 'Elisete', 'Muller', 'elisete', 'eluisete@inno.edu', 'password', 0,
        'http://www.observatoriodemarcas.com.br/images/IIcongresso/elizete_de_azevedokreutz.jpg');

-- Schools

INSERT INTO School (id, name, description, photo_url)
VALUES (${map}('0a58153cc15f4e5b802cbbf5d6c1c55c'), 'Stanford', 'Stanford is an amazing university.',
        'https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2013/07/26/100917787-Stanford_Oval_May_2011_panorama_r.jpg?v=1374868882');

INSERT INTO School (id, name, description, photo_url)
VALUES (${map}('a10afaca201644b8940b5b88323901b9'), 'Berkeley', 'Berkeley is an outstanding university.',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/UCBerkeleyCampus.jpg/220px-UCBerkeleyCampus.jpg');

INSERT INTO School (id, name, description, photo_url)
VALUES (${map}('7f297cd9723b43c98021a8530129dedb'), 'PUCRS', 'PUCRS is an outstanding university.',
        'http://www.pucrs.br/wp-content/themes/pucrs/images/logo_topo.png');

INSERT INTO School (id, name, description, photo_url)
VALUES (${map}('83768bb035514586bbba03efb5f8d7bd'), 'Massachusetts Institute of Technology',
        'MIT is an outstanding university.',
        'http://2.bp.blogspot.com/-i0BlZJ18qDQ/UawSQ7ixU1I/AAAAAAAABCQ/VjoBvrEVh5w/s1600/Postagem+12+-+M.I.T.+-+310513.bmp');

-- Mentor Profiles

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, description, rate, status)
VALUES (${map}('0e9e40c0b44b438792a99d75d10e3d42'), ${map}('ba7c650519fd47c387a6c6af6e5322b7'),
        ${map}('0a58153cc15f4e5b802cbbf5d6c1c55c'), 'feixiu@inno.edu', 'Fei is a great mentor.', 5.0, 1);

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, description, rate, status)
VALUES (${map}('e1b66612a94a4db386a104f3a102227b'), ${map}('df54ff863caa4145b228284f5d4a908a'),
        ${map}('7f297cd9723b43c98021a8530129dedb'), 'gustavo@inno.edu', 'Gustavo is a great mentor.', 10.0, 1);

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, description, rate, status)
VALUES (${map}('2744d1cbb25e4a61879dad3d15ffebe2'), ${map}('8d6153fc83e54b3a90acd081ff789cef'),
        ${map}('a10afaca201644b8940b5b88323901b9'), 'alan@inno.edu', 'Alan is a great mentor.', 15.0, 1);

-- Mentee Profiles

INSERT INTO Mentee_Profile (id, mentee_id, description)
VALUES (${map}('c5f473b4331140b18fb3f70357894754'), ${map}('e3495a43a0af42b7ab91a3801b1b56ab'),
        'Tuany is a great mentee.');

INSERT INTO Mentee_Profile (id, mentee_id, description)
VALUES (${map}('71b31ec9207d4d469e33c4b4024db0ed'), ${map}('c5e6b39233e14255a249f777b6ab355d'),
        'Eluisete is a great mentee.');

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

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status)
VALUES (${map}('f192270f2dad4bcd96c3c3765df77ce1'), ${map}('0e9e40c0b44b438792a99d75d10e3d42'),
        ${map}('c5f473b4331140b18fb3f70357894754'),
        '2018-01-02 18:00:00.000',
        '2018-01-02 19:00:00.000', 'My great first appointment.',
        1);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status)
VALUES (${map}('f192270f2dad4bcd96c3c3765df77ce2'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        ${map}('c5f473b4331140b18fb3f70357894754'),
        '2018-01-02 13:00:00.000',
        '2018-01-02 14:00:00.000', 'My great second appointment.',
        1);

-- Elisete

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status)
VALUES (${map}('f192270f2dad4bcd96c3c3765df77ce3'), ${map}('e1b66612a94a4db386a104f3a102227b'),
        ${map}('71b31ec9207d4d469e33c4b4024db0ed'), '2018-01-03 14:00:00.000',
        '2018-01-03 15:00:00.000', 'My great first appointment.', 1);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status)
VALUES (${map}('f192270f2dad4bcd96c3c3765df77ce4'), ${map}('2744d1cbb25e4a61879dad3d15ffebe2'),
        ${map}('71b31ec9207d4d469e33c4b4024db0ed'), '2018-01-03 15:00:00.000',
        '2018-01-03 16:00:00.000', 'My great second appointment.', 1);
