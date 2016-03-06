DROP DATABASE IF EXISTS backend;

CREATE DATABASE IF NOT EXISTS backend;

USE backend;

DROP TABLE IF EXISTS Person_Type;
CREATE TABLE Person_Type
(
id INT,
name VARCHAR(100),
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS Language;
CREATE TABLE Language
(
	# IANA RFC 3066 language tags (ka ISO-639 + ISO-3166)
	code VARCHAR(20) NOT NULL,
	description VARCHAR(80) NOT NULL,
	PRIMARY KEY(code)
);

#
# Person is a union of the base person plus
# student, alumni, faculty
#
DROP TABLE IF EXISTS Person;
CREATE TABLE Person
(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(200),
student_id INT,
title VARCHAR(200),
about_me TEXT(65535),
resume_url VARCHAR(200),
image_url VARCHAR(200),
email VARCHAR(200),
phone VARCHAR(200),
occupation VARCHAR(200),
company_name VARCHAR(200),
birthdate DATE,
type INT,
lang VARCHAR(20),
last_logon DATE,
password VARCHAR(255),
# student only fields
degree_major VARCHAR(200),
degree_minor VARCHAR(200),
# student/alumni shared fields
graduation_year INT,
degree_type VARCHAR(200),
# faculty field
faculty_department VARCHAR(200),
PRIMARY KEY (id),
FOREIGN KEY (type) REFERENCES Person_Type(id),
FOREIGN KEY (lang) REFERENCES Language(code),
UNIQUE KEY (email)
);

DROP TABLE IF EXISTS Job_Type;
CREATE TABLE Job_Type
(
id INT,
description VARCHAR(200),
PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Industry;
CREATE TABLE Industry
(
id INT,
name VARCHAR(200),
PRIMARY KEY(id)
);


DROP TABLE IF EXISTS Job;
CREATE TABLE Job
(
id INT NOT NULL AUTO_INCREMENT,
title VARCHAR(200),
industry INT,
location VARCHAR(200),
description TEXT(65535),
job_type INT,
start_date DATETIME,
end_date DATETIME,
posted_by_person_id INT,
hours INT,
PRIMARY KEY(id),
FOREIGN KEY(industry) REFERENCES Industry(id),
FOREIGN KEY(job_type) REFERENCES Job_Type(id),
FOREIGN KEY(posted_by_person_id) REFERENCES Person(id)
);


DROP TABLE IF EXISTS News;
CREATE TABLE News
(
id INT NOT NULL AUTO_INCREMENT,
title VARCHAR(256),
description TEXT(65535),
posted DATE,
expired DATE,
person_id INT,
views INT,
PRIMARY KEY(id),
FOREIGN KEY(person_id) REFERENCES Person(id)
);

DROP TABLE IF EXISTS Comment;
CREATE TABLE Comment
(
id INT,
news_id INT,
comment_text TEXT(65535),
comment_timestamp DATE,
PRIMARY KEY(id),
FOREIGN KEY(news_id) REFERENCES News(id)
);

DROP TABLE IF EXISTS News_Visibility_Scope;
CREATE TABLE News_Visibility_Scope
(
id INT,
news_id INT,
PRIMARY KEY(id),
FOREIGN KEY(news_id) REFERENCES News(id)
);

DROP TABLE IF EXISTS Social_Service;
CREATE TABLE Social_Service
(
id VARCHAR(60) NOT NULL,
url VARCHAR(200),
PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Social_Connection;
CREATE TABLE Social_Connection
(
id INT NOT NULL AUTO_INCREMENT,
person_id INT,
social_service_id VARCHAR(60),
url VARCHAR(200),
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
    uuid VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    date_created DATETIME,
    PRIMARY KEY(uuid)
);

