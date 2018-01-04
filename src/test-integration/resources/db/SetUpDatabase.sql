DELETE FROM User;

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('ba7c650519fd47c387a6c6af6e5322b7', 'Fei', 'Xiu', 'feixiu', 'feixiu@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('8d6153fc83e54b3a90acd081ff789cef', 'Alan', 'Ly', 'alanly', 'alanly@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('df54ff863caa4145b228284f5d4a908a', 'Gustavo', 'Di Domenico', 'gdomenico', 'gustavo@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('1e4cc51c767f4524b4e04f2e4bd249da', 'Tuany', 'Di Domenico', 'tdomenico', 'tuany@inno.edu', 'password');

DELETE FROM School;

INSERT INTO School (id, name, description)
VALUES ('0a58153cc15f4e5b802cbbf5d6c1c55c', 'Stanford', 'Stanford is an amazing university.');

INSERT INTO School (id, name, description)
VALUES ('a10afaca201644b8940b5b88323901b9', 'Berkeley', 'Berkeley is an outstanding university.');

DELETE FROM Profile;

INSERT INTO Profile (id, user_id, description)
VALUES ('c5f473b4331140b18fb3f70357894754', '8d6153fc83e54b3a90acd081ff789cef',
        'Alan is a great mentee.');

INSERT INTO Profile (id, user_id, school_id, description, rate)
VALUES ('0e9e40c0b44b438792a99d75d10e3d42', 'ba7c650519fd47c387a6c6af6e5322b7',
        '0a58153cc15f4e5b802cbbf5d6c1c55c', 'Fei is a great mentor.', 10.5);

INSERT INTO Profile (id, user_id, school_id, description, rate)
VALUES ('e1b66612a94a4db386a104f3a102227b', 'df54ff863caa4145b228284f5d4a908a',
        '0a58153cc15f4e5b802cbbf5d6c1c55c', 'Gustavo is a great mentor.', 20.5);

DELETE FROM Profile_Association;

INSERT INTO Profile_Association (id, profile_id, school_id, status, description)
VALUES ('a29aab9470044ac69081ebea3890df51', '0e9e40c0b44b438792a99d75d10e3d42',
        '0a58153cc15f4e5b802cbbf5d6c1c55c', 1, 'Approved');

DELETE FROM Availability;

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('0c55130c6e63420bb3aeb2485caadc23', '0e9e40c0b44b438792a99d75d10e3d42',
        '2017-11-09 12:00:01.000', '2017-11-10 12:00:01.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('8c0af3b9288340f3bf8526ee0f506fde', '0e9e40c0b44b438792a99d75d10e3d42',
        '2017-12-09 12:00:01.000', '2017-12-10 12:00:01.000');

DELETE FROM Appointment;

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES ('f192270f2dad4bcd96c3c3765df77ce8', '0e9e40c0b44b438792a99d75d10e3d42',
        'c5f473b4331140b18fb3f70357894754', '2017-11-10 09:00:01.000',
        '2017-11-10 10:00:01.000', 'My great first appointment.', 0, 10.5);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES ('42f2431a0216416eb79529292b637ec4', '0e9e40c0b44b438792a99d75d10e3d42',
        'c5f473b4331140b18fb3f70357894754', '2017-11-10 10:00:01.000',
        '2017-11-10 11:00:01.000', 'My great second appointment.', 1, 10.5);

DELETE FROM Feedback;

INSERT INTO Feedback (id, appointment_id, source, rating, description)
VALUES ('bd06f884b1264be8b637758519dea5a5', 'f192270f2dad4bcd96c3c3765df77ce8', 0, 5, 'Great session');
