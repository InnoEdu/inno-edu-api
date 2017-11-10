delete from User;
insert into User (id, first_name, last_name) values ('ba7c6505-19fd-47c3-87a6-c6af6e5322b7', 'FirstName', 'LastName');
insert into User (id, first_name, last_name) values ('8d6153fc-83e5-4b3a-90ac-d081ff789cef', 'OtherFirstName', 'OtherLastName');

delete from University;
insert into University (id, name) values ('0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', 'Name');
insert into University (id, name) values ('a10afaca-2016-44b8-940b-5b88323901b9', 'OtherName');

delete from Availability;
insert into Availability (id, user_id, university_id, from_date_time, to_date_time)
values ('0c55130c-6e63-420b-b3ae-b2485caadc23', 'ba7c6505-19fd-47c3-87a6-c6af6e5322b7', '0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', NOW(), NOW());

insert into Availability (id, user_id, university_id, from_date_time, to_date_time)
values ('8c0af3b9-2883-40f3-bf85-26ee0f506fde', 'ba7c6505-19fd-47c3-87a6-c6af6e5322b7', '0a58153c-c15f-4e5b-802c-bbf5d6c1c55c', NOW(), NOW());
