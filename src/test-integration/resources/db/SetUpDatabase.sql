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

INSERT INTO Mentor_Profile (id, mentor_id, school_id, email, description, status)
VALUES ('0e9e40c0-b44b-4387-92a9-9d75d10e3d42', 'ba7c6505-19fd-47c3-87a6-c6af6e5322b7',
        '0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', 'feixiu@inno.edu', 'Fei is a great mentor.', 1);

DELETE FROM Availability;

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('0c55130c-6e63-420b-b3ae-b2485caadc23', '0e9e40c0-b44b-4387-92a9-9d75d10e3d42',
        '2017-11-09 12:00:01.000', '2017-11-10 12:00:01.000');

INSERT INTO Availability (id, mentor_profile_id, from_date_time, to_date_time)
VALUES ('8c0af3b9-2883-40f3-bf85-26ee0f506fde', '0e9e40c0-b44b-4387-92a9-9d75d10e3d42',
        '2017-12-09 12:00:01.000', '2017-12-10 12:00:01.000');

DELETE FROM Appointment;

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, status)
VALUES ('f192270f-2dad-4bcd-96c3-c3765df77ce8', '0e9e40c0-b44b-4387-92a9-9d75d10e3d42',
        'c5f473b4-3311-40b1-8fb3-f70357894754', '2017-11-10 09:00:01.000',
        '2017-11-10 10:00:01.000', 0);

INSERT INTO Appointment (id, mentor_profile_id, mentee_profile_id, from_date_time, to_date_time, status)
VALUES ('42f2431a-0216-416e-b795-29292b637ec4', '0e9e40c0-b44b-4387-92a9-9d75d10e3d42',
        'c5f473b4-3311-40b1-8fb3-f70357894754', '2017-11-10 10:00:01.000',
        '2017-11-10 11:00:01.000', 1);
