DELETE FROM User;

INSERT INTO User (id, first_name, last_name, user_name, is_mentor, photo_url)
VALUES ('ba7c650519fd47c387a6c6af6e5322b7', 'Fei', 'Xiu', 'feixiu', 1, 'https://i1.rgstatic.net/ii/profile.image/AS%3A278674336174081%401443452547142_xl/Peng_Fei_Xu.png');

INSERT INTO User (id, first_name, last_name, user_name, is_mentor, photo_url)
VALUES ('8d6153fc83e54b3a90acd081ff789cef', 'Alan', 'Ly', 'alanly', 0, 'https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAeBAAAAJDk1ZDQyNzE0LWY2MGQtNDFmYS05MmRmLTRhMzc0MTAxMGEwMQ.jpg');

DELETE FROM Mentee_Profile;

INSERT INTO Mentee_Profile (id, mentee_id, email, description)
VALUES ('c5f473b4331140b18fb3f70357894754', '8d6153fc83e54b3a90acd081ff789cef', 'alanly@inno.edu', 'Alan is a great mentee.');

DELETE FROM School;

INSERT INTO School (id, name, description)
VALUES ('0a58153cc15f4e5b802cbbf5d6c1c55c', 'Stanford', 'Stanford is an amazing university.');

INSERT INTO School (id, name, description)
VALUES ('a10afaca201644b8940b5b88323901b9', 'Berkeley', 'Berkeley is an outstanding university.');

DELETE FROM Mentor_Profile;

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, description, status)
VALUES ('0e9e40c0b44b438792a99d75d10e3d42', 'ba7c650519fd47c387a6c6af6e5322b7',
        '0a58153cc15f4e5b802cbbf5d6c1c55c', 'feixiu@inno.edu', 'Fei is a great mentor.', 1);

DELETE FROM Availability;

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('0c55130c6e63420bb3aeb2485caadc23', '0e9e40c0b44b438792a99d75d10e3d42',
        '2017-11-09 12:00:01.000', '2017-11-10 12:00:01.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('8c0af3b9288340f3bf8526ee0f506fde', '0e9e40c0b44b438792a99d75d10e3d42',
        '2017-12-09 12:00:01.000', '2017-12-10 12:00:01.000');

DELETE FROM Appointment;

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status)
VALUES ('f192270f2dad4bcd96c3c3765df77ce8', '0e9e40c0b44b438792a99d75d10e3d42',
        'c5f473b4331140b18fb3f70357894754', '2017-11-10 09:00:01.000',
        '2017-11-10 10:00:01.000', 'My great first appointment.', 0);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status)
VALUES ('42f2431a0216416eb79529292b637ec4', '0e9e40c0b44b438792a99d75d10e3d42',
        'c5f473b4331140b18fb3f70357894754', '2017-11-10 10:00:01.000',
        '2017-11-10 11:00:01.000', 'My great second appointment.', 1);
