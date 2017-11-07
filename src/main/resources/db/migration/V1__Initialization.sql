CREATE TABLE User (
	id BIGINT auto_increment,
	first_name varchar(255) not null,
	last_name varchar(255) not null
);

insert into User (first_name, last_name) values ('Dave', 'Syer');
