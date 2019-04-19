-- *************************************************************************************************
-- This script creates all of the database objects (tables, sequences, etc) for the database
-- *************************************************************************************************

BEGIN;

-- CREATE statements go here
-- DROP TABLE IF EXISTS app_user;



CREATE TABLE family (
  	family_id SERIAL PRIMARY KEY,
 	family_name varchar(32) NOT NULL
  
);

CREATE TABLE account (
	user_name varchar(100) PRIMARY KEY,
	password varchar(25),
	family_id int NOT NULL, 
	is_owner boolean,
	salt varchar(255) NOT NULL,
	
	constraint fk_user_family_family_id foreign key (family_id) references family (family_id)
); 

CREATE TABLE people (
	people_id SERIAL PRIMARY KEY,
	family_id int,
	name varchar(100),
	is_parent boolean,
	inactive boolean DEFAULT false,
	
	constraint fk_people_family_family_id foreign key (family_id) references family (family_id)
);


CREATE TABLE book (
	book_id SERIAL PRIMARY KEY,
	title varchar(255) NOT NULL,
	author varchar(200) NOT NULL,
	isbn bigint UNIQUE,
	image varchar(600)
	
);



CREATE TABLE people_book (
	people_book_id SERIAL PRIMARY KEY,
	book_id int NOT NULL,
	people_id int NOT NULL,
	inactive boolean,
	
	constraint fk_people_book_book_id foreign key (book_id) references book (book_id),
	constraint fk_people_book_people_id foreign key (people_id) references people (people_id)
);

CREATE TABLE reading_type (
	reading_type_id SERIAL PRIMARY KEY,
	reading_type varchar(100) NOT NULL
);

CREATE TABLE session (
	session_id SERIAL PRIMARY KEY,
	people_book_id int NOT NULL,
	date_of_reading date NOT NULL,
	minutes_read int NOT NULL,
	type_of_reading int,
	
	constraint fk_session_people_book_id foreign key (people_book_id) references people_book (people_book_id)
);



CREATE TABLE goal (
	goal_id SERIAL PRIMARY KEY,
	name_of_goal varchar(150),
	start_date date NOT NULL,
	number_of_days int NOT NULL,
	description varchar(500),
	minutes_to_reach_goal int NOT NULL,
	minutes_read int,
	is_complete boolean
);

CREATE TABLE goal_people (
	goal_id int NOT NULL,
	people_id int NOT NULL,
	
	constraint fk_goal_people_goal_id foreign key (goal_id) references goal (goal_id),
	constraint fk_goal_people_people_id foreign key (people_id) references people (people_id)
);

CREATE TABLE competition (
	competition_id SERIAL PRIMARY KEY,
	name_of_competition varchar(150),
	start_date date NOT NULL,
	end_date date NOT NULL,
	description varchar,
	minutes_to_finish int NOT NULL,
	family_id int NOT NULL,
	
	constraint fk_competition_family_id foreign key (family_id) references family (family_id)
	
);

CREATE TABLE competition_people (
	competition_id int NOT NULL,
	people_id int NOT NULL,
	
	constraint fk_competition_people_competition_id foreign key (competition_id) references competition (competition_id),
	constraint fk_competition_people_people_id foreign key (people_id) references people (people_id)
);



INSERT INTO family (family_name) VALUES ('The Null Pointers');
INSERT INTO account (user_name, password, family_id, is_owner, salt) VALUES ('abc@def.com', 'KRlSVnHn6JB/u0xtzhxgKA==', 1, true, 'LoZOO1KJLY0PfvoXrgi4Cb1elq2QazTAcIawXPjlWnvXhLmd826LKdbvKKgmmXC05BNWg0b45kCkVnFpBtEDHhIkFjvpsY2GlR2CU3nXP5uGPm2LjNC6hK7Kj5Eo7mwK3vTBwo5L86mCkf9531ni9ULZEa/VZdhnu/PZeCojg44=');
INSERT INTO people (family_id, name, is_parent, inactive) VALUES (1, 'Pepper', true, false);
INSERT INTO people (family_id, name, is_parent, inactive) VALUES (1, 'Oregano', true, false);
INSERT INTO people (family_id, name, is_parent, inactive) VALUES (1, 'Salt', false, false);
INSERT INTO people (family_id, name, is_parent, inactive) VALUES (1, 'Cumin', false, false);
INSERT INTO goal (name_of_goal, start_date, number_of_days, description, minutes_to_reach_goal, minutes_read, is_complete) VALUES ('Weekly Goal for Pepper', '04/12/2019', 7, 'Read 100 minutes per week', 100, 0, false);
INSERT INTO goal (name_of_goal, start_date, number_of_days, description, minutes_to_reach_goal, minutes_read, is_complete) VALUES ('Weekly Goal for Oregano', '04/12/2019', 7, 'Read 100 minutes per week', 100, 0, false);
INSERT INTO goal (name_of_goal, start_date, number_of_days, description, minutes_to_reach_goal, minutes_read, is_complete) VALUES ('Weekly Goal for Salt', '04/12/2019', 30, 'Read 1000 minutes per month', 1000, 0, false);
INSERT INTO goal (name_of_goal, start_date, number_of_days, description, minutes_to_reach_goal, minutes_read, is_complete) VALUES ('Weekly Goal for Cumin', '04/12/2019', 14, 'Read 500 minutes per two week', 500, 0, false);
INSERT INTO competition (name_of_competition, start_date, end_date, description, minutes_to_finish, family_id) VALUES ('Family month competition', '03/01/2019', '05/30/2019', 'First two people to read 1000 minutes', 1000, 1);
INSERT INTO goal_people (goal_id, people_id) VALUES (1, 1);
INSERT INTO goal_people (goal_id, people_id) VALUES (2, 2);
INSERT INTO goal_people (goal_id, people_id) VALUES (3, 3);
INSERT INTO goal_people (goal_id, people_id) VALUES (4, 4);
INSERT INTO competition_people (competition_id, people_id) VALUES (1, 1);
INSERT INTO competition_people (competition_id, people_id) VALUES (1, 2);
INSERT INTO competition_people (competition_id, people_id) VALUES (1, 3);
INSERT INTO competition_people (competition_id, people_id) VALUES (1, 4);
INSERT INTO book (title, author, isbn, image) VALUES ('Hop on Pop', 'Dr. Seuss', 0375828370, 'https://covers.openlibrary.org/b/id/7130596-L.jpg');
INSERT INTO book (title, author, isbn, image) VALUES ('Gray''s anatomy', 'Spalding Gray', 0679751785, 'https://ia800604.us.archive.org/zipview.php?zip=/1/items/olcovers42/olcovers42-L.zip&file=420864-L.jpg');
INSERT INTO book (title, author, isbn, image) VALUES ('Firestarter', 'Stephen King', 0451150317, 'https://ia800300.us.archive.org/BookReader/BookReaderPreview.php?id=firestartersigne00step&itemPath=%2F18%2Fitems%2Ffirestartersigne00step&server=ia800300.us.archive.org&page=cover_w500_h500.jpg');
INSERT INTO book (title, author, isbn, image) VALUES ('Christmas With Eve (It Happened One Night...)', 'Elda Minger', 0373257147, 'https://ia800303.us.archive.org/BookReader/BookReaderPreview.php?id=christmaswitheve00ming&itemPath=%2F3%2Fitems%2Fchristmaswitheve00ming&server=ia800303.us.archive.org&page=cover_w500_h500.jpg');
INSERT INTO reading_type (reading_type) VALUES ('Read-Aloud (reader)');
INSERT INTO reading_type (reading_type) VALUES ('Read-Aloud (listener)');
INSERT INTO reading_type (reading_type) VALUES ('Listened_To');
INSERT INTO reading_type (reading_type) VALUES ('Read-To_Self');
INSERT INTO people_book (book_id, people_id) VALUES (1, 1);
INSERT INTO people_book (book_id, people_id) VALUES (1, 2);
INSERT INTO people_book (book_id, people_id) VALUES (2, 1);
INSERT INTO people_book (book_id, people_id) VALUES (3, 3);
INSERT INTO people_book (book_id, people_id) VALUES (4, 4);
INSERT INTO people_book (book_id, people_id) VALUES (2, 3);
INSERT INTO session (people_book_id, date_of_reading, minutes_read, type_of_reading) VALUES (1, '03/12/2019', 15, 1);
INSERT INTO session (people_book_id, date_of_reading, minutes_read, type_of_reading) VALUES (2, '03/12/2019', 20, 2);
INSERT INTO session (people_book_id, date_of_reading, minutes_read, type_of_reading) VALUES (3, '03/11/2019', 30, 3);
INSERT INTO session (people_book_id, date_of_reading, minutes_read, type_of_reading) VALUES (4, '03/01/2019', 15, 4);




COMMIT;