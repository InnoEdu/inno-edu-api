DELETE FROM Feedback;
DELETE FROM Appointment;
DELETE FROM Availability;
DELETE FROM Service;
DELETE FROM Accomplishment;
DELETE FROM Interest;
DELETE FROM Profile_Association;
DELETE FROM Experience;
DELETE FROM Profile_Attachment;
DELETE FROM School_Attachment;
DELETE FROM Profile;
DELETE FROM School;
DELETE FROM Attachment;
DELETE FROM User;
DELETE FROM Skill;

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('ba7c650519fd47c387a6c6af6e5322b7', 'Fei', 'Xiu', 'feixiu', 'feixiu@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('e79b6b2ecc084e37b22751cd64f5aa2e', 'Fei to delete', 'Xiu', 'feixiu2', 'feixiu@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('8d6153fc83e54b3a90acd081ff789cef', 'Alan', 'Ly', 'alanly', 'alanly@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('df54ff863caa4145b228284f5d4a908a', 'Gustavo', 'Di Domenico', 'gdomenico', 'gustavo@inno.edu', 'password');

INSERT INTO User (id, first_name, last_name, username, email, password)
VALUES ('1e4cc51c767f4524b4e04f2e4bd249da', 'Tuany', 'Di Domenico', 'tdomenico', 'tuany@inno.edu', 'password');

INSERT INTO School (id, name, description)
VALUES ('0a58153cc15f4e5b802cbbf5d6c1c55c', 'Stanford', 'Stanford is an amazing university.');

INSERT INTO School (id, name, description)
VALUES ('46ddda8ee01e430f99ede8aee71db79a', 'Stanford to delete', 'Stanford is an amazing university.');

INSERT INTO School (id, name, description)
VALUES ('a10afaca201644b8940b5b88323901b9', 'Berkeley', 'Berkeley is an outstanding university.');

INSERT INTO Profile (id, user_id, description, location)
VALUES ('c5f473b4331140b18fb3f70357894754', '8d6153fc83e54b3a90acd081ff789cef',
        'Alan is a great mentee.', 'San Francisco, CA');

INSERT INTO Profile (id, user_id, description, location)
VALUES ('88447c0d3f2e48d9ae5183037465ab47', 'ba7c650519fd47c387a6c6af6e5322b7',
        'Fei to delete is a great mentor.', 'San Francisco, CA');

INSERT INTO Profile (id, user_id, school_id, description, rate, location)
VALUES ('0e9e40c0b44b438792a99d75d10e3d42', 'ba7c650519fd47c387a6c6af6e5322b7',
        '0a58153cc15f4e5b802cbbf5d6c1c55c', 'Fei is a great mentor.', 10.5, 'San Francisco, CA');

INSERT INTO Profile (id, user_id, school_id, description, rate, location, company, profile_reference_id)
VALUES ('e1b66612a94a4db386a104f3a102227b', 'df54ff863caa4145b228284f5d4a908a',
        '0a58153cc15f4e5b802cbbf5d6c1c55c', 'Gustavo is a great mentor.', 20.5, 'San Francisco, CA', 'Company', '0e9e40c0b44b438792a99d75d10e3d42');

INSERT INTO Attachment(id, description, url)
VALUES ('0a2de2db3997408282b656aad9746af9', 'My file', '');

INSERT INTO Attachment(id, description, url)
VALUES ('a58c72bf8444491ea887f2a8c6345520', 'My second file', '');

INSERT INTO Attachment(id, description, url)
VALUES ('b192eba5264b4c329bfee72d1f7a244b', 'My file to delete', '');

INSERT INTO Profile_Attachment(profile_id, attachment_id)
VALUES ('0e9e40c0b44b438792a99d75d10e3d42', '0a2de2db3997408282b656aad9746af9');

INSERT INTO School_Attachment(school_id, attachment_id)
VALUES ('0a58153cc15f4e5b802cbbf5d6c1c55c', 'a58c72bf8444491ea887f2a8c6345520');

INSERT INTO Profile_Association (id, profile_id, school_id, status, description)
VALUES ('a29aab9470044ac69081ebea3890df51', '0e9e40c0b44b438792a99d75d10e3d42',
        '0a58153cc15f4e5b802cbbf5d6c1c55c', 1, 'Approved');

INSERT INTO Experience (id, profile_id, title, area, institution, location, from_date, to_date, description, type)
VALUES ('7555b5cef7a04d9aa90225f9dcc8de6f', '0e9e40c0b44b438792a99d75d10e3d42', 'Owner', 'Area', 'InnoEdu', 'San Francisco, CA', '2018-01-01', '2018-12-31', 'Great owner.', 0);

INSERT INTO Interest (id, profile_id, title, description)
VALUES ('f6c17afc39d2475b827a3f473db678af', '0e9e40c0b44b438792a99d75d10e3d42', 'My interest', 'Perfect interest.');

INSERT INTO Accomplishment (id, profile_id, title, description, type)
VALUES ('27f8f74507e24fb896d45c6623c5cbc6', '0e9e40c0b44b438792a99d75d10e3d42', 'My accomplishment', 'Perfect accomplishment.', 0);

INSERT INTO Service (id, profile_id, title, description)
VALUES ('a9e747ab1cb9494196085f6115bb48ce', '0e9e40c0b44b438792a99d75d10e3d42', 'My service', 'Perfect service.');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('0c55130c6e63420bb3aeb2485caadc23', '0e9e40c0b44b438792a99d75d10e3d42',
        '2017-11-09 12:00:01.000', '2017-11-10 12:00:01.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('8c0af3b9288340f3bf8526ee0f506fde', '0e9e40c0b44b438792a99d75d10e3d42',
        '2017-12-09 12:00:01.000', '2017-12-10 12:00:01.000');

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES ('f192270f2dad4bcd96c3c3765df77ce8', '0e9e40c0b44b438792a99d75d10e3d42',
        'c5f473b4331140b18fb3f70357894754', '2017-11-10 09:00:01.000',
        '2017-11-10 10:00:01.000', 'My great first appointment.', 0, 10.5);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES ('42f2431a0216416eb79529292b637ec4', '0e9e40c0b44b438792a99d75d10e3d42',
        'c5f473b4331140b18fb3f70357894754', '2017-11-10 10:00:01.000',
        '2017-11-10 11:00:01.000', 'My great second appointment.', 1, 10.5);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES ('2bb42516808d4b2e940c2eba440ac378', '0e9e40c0b44b438792a99d75d10e3d42',
        'c5f473b4331140b18fb3f70357894754', '2017-11-10 09:00:01.000',
        '2017-11-10 10:00:01.000', 'Appoointment to delete.', 0, 10.5);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, description, status, fee)
VALUES ('22f81159e2114b9594166648c339ce3a', '0e9e40c0b44b438792a99d75d10e3d42',
        'c5f473b4331140b18fb3f70357894754', '2017-11-10 09:00:05.000',
        '2017-11-10 10:00:05.000', 'conflict', 0, 10.5);

INSERT INTO Feedback (id, appointment_id, source, rating, description)
VALUES ('bd06f884b1264be8b637758519dea5a5', 'f192270f2dad4bcd96c3c3765df77ce8', 0, 5, 'Great session');

-- Skills

INSERT INTO Skill (id, title, description)
VALUES ('52c048db062f42a6918de9aeea8d3a0d', 'Communication', 'Description.');

INSERT INTO Skill (id, title, description)
VALUES ('094dad9244884b369c27dcfde9a1a32d', 'Mentoring', 'Description.');
