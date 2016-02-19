DROP DATABASE IF EXISTS backend;

CREATE DATABASE IF NOT EXISTS backend;

USE backend;

DROP TABLE IF EXISTS Person_Type;
CREATE TABLE Person_Type
(
id int,
name varchar(100),
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS Person;
CREATE TABLE Person
(
id int NOT NULL AUTO_INCREMENT,
name varchar(200),
student_id int,
title varchar(200),
about_me TEXT(65535),
resume_url varchar(200),
image_url varchar(200),
email varchar(200),
phone varchar(200),
occupation varchar(200),
company_name varchar(200),
birthdate DATE,
type int,
last_logon DATE,
password varchar(255),
PRIMARY KEY (id),
FOREIGN KEY (type) REFERENCES Person_Type(id)
);

DROP TABLE IF EXISTS Faculty;
CREATE TABLE Faculty
(
id int,
person_id int,
faculty_department varchar(255),
PRIMARY KEY (id),
FOREIGN KEY (person_id) REFERENCES Person(id)
);

DROP TABLE IF EXISTS Alumni;
CREATE TABLE Alumni
(
id int,
person_id int,
alumni_gradyear int,
alumni_degree varchar(256),
PRIMARY KEY (id),
FOREIGN KEY (person_id) REFERENCES Person(id)
);


DROP TABLE IF EXISTS Current_Student;
CREATE TABLE Current_Student
(
id int,
person_id int,
major varchar(256),
minor varchar(256),
graduation_date varchar(256),
PRIMARY KEY(id),
FOREIGN KEY (person_id) REFERENCES Person(id)
);

DROP TABLE IF EXISTS Job_Type;
CREATE TABLE Job_Type
(
id int,
description varchar(200),
PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Industry;
CREATE TABLE Industry
(
id int,
name varchar(200),
PRIMARY KEY(id)
);


DROP TABLE IF EXISTS Job;
CREATE TABLE Job
(
id int,
title varchar(200),
industry int,
location varchar(200),
description TEXT(65535),
job_type int,
start_date DATE,
end_date DATE,
posted_by_person_id int,
hours int,
PRIMARY KEY(id),
FOREIGN KEY(industry) REFERENCES Industry(id),
FOREIGN KEY(job_type) REFERENCES Job_Type(id),
FOREIGN KEY(posted_by_person_id) REFERENCES Person(id)
);


DROP TABLE IF EXISTS News;
CREATE TABLE News
(
id int,
news_title varchar(256),
news_description TEXT(65535),
news_posted DATE,
new_expired DATE,
news_person_id int,
news_views int,
PRIMARY KEY(id),
FOREIGN KEY(news_person_id) REFERENCES Person(id)
);

DROP TABLE IF EXISTS Comment;
CREATE TABLE Comment
(
id int,
news_id int,
comment_text TEXT(65535),
comment_timestamp DATE,
PRIMARY KEY(id),
FOREIGN KEY(news_id) REFERENCES News(id)
);

DROP TABLE IF EXISTS News_Visibility_Scope;
CREATE TABLE News_Visibility_Scope
(
id int,
news_id int,
PRIMARY KEY(id),
FOREIGN KEY(news_id) REFERENCES News(id)
);

DROP TABLE IF EXISTS Social_Service;
CREATE TABLE Social_Service
(
id int,
name varchar(200),
url varchar(200),
PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Social_Connections;
CREATE TABLE Social_Connections
(
id int,
person_id int,
social_service_id int,
url varchar(200),
PRIMARY KEY(id),
FOREIGN KEY(person_id) REFERENCES Person(id),
FOREIGN KEY(social_service_id) REFERENCES Social_Service(id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles(
id INT NOT NULL, 
rolename VARCHAR(60)
);

