delete from User;
insert into User (id, first_name, last_name) values ('ba7c6505-19fd-47c3-87a6-c6af6e5322b7', 'Fei', 'Xiu');
insert into User (id, first_name, last_name) values ('8d6153fc-83e5-4b3a-90ac-d081ff789cef', 'Alan', 'LastName');

delete from University;
insert into University (id, name) values ('0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', 'Stanford');
insert into University (id, name) values ('a10afaca-2016-44b8-940b-5b88323901b9', 'Berkeley');

delete from Availability;
insert into Availability (id, user_id, university_id, from_date_time, to_date_time)
values ('0c55130c-6e63-420b-b3ae-b2485caadc23', 'ba7c6505-19fd-47c3-87a6-c6af6e5322b7', '0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', '2017-11-09 12:00:01.000', '2017-11-10 12:00:01.000');

insert into Availability (id, user_id, university_id, from_date_time, to_date_time)
values ('8c0af3b9-2883-40f3-bf85-26ee0f506fde', '8d6153fc-83e5-4b3a-90ac-d081ff789cef', 'a10afaca-2016-44b8-940b-5b88323901b9', '2017-12-09 12:00:01.000', '2017-12-10 12:00:01.000');
