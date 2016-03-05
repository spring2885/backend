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

DROP TABLE IF EXISTS Language;
CREATE TABLE Language
(
	# IANA RFC 3066 language tags (ka ISO-639 + ISO-3166)
	code varchar(20) NOT NULL,
	description varchar(80) NOT NULL,
	PRIMARY KEY(code)
);

#
# Person is a union of the base person plus
# student, alumni, faculty
#
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
lang varchar(20),
last_logon DATE,
password varchar(255),
# student only fields
degree_major varchar(200),
degree_minor varchar(200),
# student/alumni shared fields
graduation_year int,
degree_type varchar(200),
# faculty field
faculty_department varchar(200),
PRIMARY KEY (id),
FOREIGN KEY (type) REFERENCES Person_Type(id),
FOREIGN KEY (lang) REFERENCES Language(code)
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
id varchar(60) NOT NULL,
url varchar(200),
PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Social_Connection;
CREATE TABLE Social_Connection
(
id int NOT NULL AUTO_INCREMENT,
person_id int,
social_service_id varchar(60),
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

DROP TABLE IF EXISTS token;
CREATE TABLE token(
    id int NOT NULL,    
    email varchar(200),
    uuid varchar(200),
    uuidStatus varchar (20),
    dateCreate DATE,
    PRIMARY KEY(uuid)
);

