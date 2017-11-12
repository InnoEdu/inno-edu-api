DELETE FROM User;

INSERT INTO User (id, first_name, last_name, user_name, is_mentor) VALUES ('ba7c6505-19fd-47c3-87a6-c6af6e5322b7', 'Fei', 'Xiu', 'feixiu', 1);
INSERT INTO User (id, first_name, last_name, user_name, is_mentor) VALUES ('8d6153fc-83e5-4b3a-90ac-d081ff789cef', 'Alan', 'Ly', 'alanly',0);

DELETE FROM University;

INSERT INTO University (id, name) VALUES ('0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', 'Stanford');
INSERT INTO University (id, name) VALUES ('a10afaca-2016-44b8-940b-5b88323901b9', 'Berkeley');

DELETE FROM Availability;

INSERT INTO Availability (id, mentor_id, university_id, from_date_time, to_date_time)
VALUES ('0c55130c-6e63-420b-b3ae-b2485caadc23', 'ba7c6505-19fd-47c3-87a6-c6af6e5322b7',
        '0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', '2017-11-09 12:00:01.000', '2017-11-10 12:00:01.000');

INSERT INTO Availability (id, mentor_id, university_id, from_date_time, to_date_time)
VALUES ('8c0af3b9-2883-40f3-bf85-26ee0f506fde', '8d6153fc-83e5-4b3a-90ac-d081ff789cef',
        'a10afaca-2016-44b8-940b-5b88323901b9', '2017-12-09 12:00:01.000', '2017-12-10 12:00:01.000');

DELETE FROM Appointment;

INSERT INTO Appointment (id, mentor_id, mentee_id, university_id, from_date_time, to_date_time, status)
VALUES ('f192270f-2dad-4bcd-96c3-c3765df77ce8', 'ba7c6505-19fd-47c3-87a6-c6af6e5322b7',
        '8d6153fc-83e5-4b3a-90ac-d081ff789cef', '0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', '2017-11-10 09:00:01.000',
        '2017-11-10 10:00:01.000', 0);

INSERT INTO Appointment (id, mentor_id, mentee_id, university_id, from_date_time, to_date_time, status)
VALUES ('42f2431a-0216-416e-b795-29292b637ec4', 'ba7c6505-19fd-47c3-87a6-c6af6e5322b7',
        '8d6153fc-83e5-4b3a-90ac-d081ff789cef', '0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', '2017-11-10 10:00:01.000',
        '2017-11-10 11:00:01.000', 1);
