CREATE TABLE User (
	id UUID PRIMARY KEY,
	first_name varchar(255) not null,
	last_name varchar(255) not null
);

CREATE TABLE University (
	id UUID PRIMARY KEY,
	name varchar(255) not null
);