CREATE TABLE User (
  id         BINARY(16) PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name  VARCHAR(50) NOT NULL,
  username   VARCHAR(30)  NOT NULL,
  email      VARCHAR(255) NOT NULL,
  password   VARCHAR(30)  NOT NULL,
  photo_url  VARCHAR(255),
  is_mentor  BOOLEAN      NOT NULL
);

CREATE TABLE Mentee_Profile (
  id          BINARY(16) PRIMARY KEY,
  mentee_id   BINARY(16)   NOT NULL,
  description TEXT         NOT NULL,

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
VALUES ('ba7c650519fd47c387a6c6af6e5322b7', 'Fei', 'Xiu', 'feixiu', 'feixiu@inno.edu', 'password', 1,
'https://i1.rgstatic.net/ii/profile.image/AS%3A278674336174081%401443452547142_xl/Peng_Fei_Xu.png');

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES ('8d6153fc83e54b3a90acd081ff789cef', 'Alan', 'Ly', 'alanly', 'alanly@inno.edu', 'password', 1,
        'https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAeBAAAAJDk1ZDQyNzE0LWY2MGQtNDFmYS05MmRmLTRhMzc0MTAxMGEwMQ.jpg');

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES ('df54ff863caa4145b228284f5d4a908a', 'Gustavo', 'Di Domenico', 'gdomenico', 'gustavo@inno.edu', 'password', 1,
        'https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAANPAAAAJGMwMjU0ZWJlLTBjZmEtNDNiNC1hZWVlLTQzNDRjZDM1MDZjZQ.jpg');

-- Mentees

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES ('e3495a43a0af42b7ab91a3801b1b56ab', 'Tuany', 'Di Domenico', 'tuany', 'tuany@inno.edu', 'password', 0,
        'https://scontent-sea1-1.cdninstagram.com/t51.2885-15/s480x480/e15/c0.80.640.640/13129262_634106980078940_906222652_n.jpg?ig_cache_key=MTI0MDAxMzMyNzYzMDI1ODQyOQ%3D%3D.2.c');

INSERT INTO User (id, first_name, last_name, username, email, password, is_mentor, photo_url)
VALUES ('c5e6b39233e14255a249f777b6ab355d', 'Eluisete', 'Muller', 'eluisete', 'eluisete@inno.edu', 'password', 0,
        'http://www.observatoriodemarcas.com.br/images/IIcongresso/elizete_de_azevedokreutz.jpg');

-- Schools

INSERT INTO School (id, name, description, photo_url)
VALUES ('0a58153cc15f4e5b802cbbf5d6c1c55c', 'Stanford', 'Stanford is an amazing university.',
        'https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2013/07/26/100917787-Stanford_Oval_May_2011_panorama_r.jpg?v=1374868882');

INSERT INTO School (id, name, description, photo_url)
VALUES ('a10afaca201644b8940b5b88323901b9', 'Berkeley', 'Berkeley is an outstanding university.',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/UCBerkeleyCampus.jpg/220px-UCBerkeleyCampus.jpg');

INSERT INTO School (id, name, description, photo_url)
VALUES ('7f297cd9723b43c98021a8530129dedb', 'PUCRS', 'PUCRS is an outstanding university.',
        'http://www.pucrs.br/wp-content/themes/pucrs/images/logo_topo.png');

INSERT INTO School (id, name, description, photo_url)
VALUES ('83768bb035514586bbba03efb5f8d7bd', 'Massachusetts Institute of Technology', 'MIT is an outstanding university.',
        'http://2.bp.blogspot.com/-i0BlZJ18qDQ/UawSQ7ixU1I/AAAAAAAABCQ/VjoBvrEVh5w/s1600/Postagem+12+-+M.I.T.+-+310513.bmp');

-- Mentor Profiles

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, description, status)
VALUES ('0e9e40c0b44b438792a99d75d10e3d42', 'ba7c650519fd47c387a6c6af6e5322b7',
        '0a58153cc15f4e5b802cbbf5d6c1c55c', 'feixiu@inno.edu', 'Fei is a great mentor.', 1);

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, description, status)
VALUES ('e1b66612a94a4db386a104f3a102227b', 'df54ff863caa4145b228284f5d4a908a',
        '7f297cd9723b43c98021a8530129dedb', 'gustavo@inno.edu', 'Gustavo is a great mentor.', 1);

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, description, status)
VALUES ('2744d1cbb25e4a61879dad3d15ffebe2', '8d6153fc83e54b3a90acd081ff789cef',
        'a10afaca201644b8940b5b88323901b9', 'alan@inno.edu', 'Alan is a great mentor.', 1);




