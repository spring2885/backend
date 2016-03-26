DROP DATABASE IF EXISTS backend;

CREATE DATABASE IF NOT EXISTS backend;

USE backend;

DROP TABLE IF EXISTS person_type;
CREATE TABLE person_type
(
id INT,
name VARCHAR(100),
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS language;
CREATE TABLE language
(
	# IANA RFC 3066 language tags (ka ISO-639 + ISO-3166)
	code VARCHAR(20) NOT NULL,
	description VARCHAR(80) NOT NULL,
	PRIMARY KEY(code)
);

#
# person is a union of the base person plus
# student, alumni, faculty
#
DROP TABLE IF EXISTS person;
CREATE TABLE person
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
active TINYINT(1) DEFAULT 1,
PRIMARY KEY (id),
FOREIGN KEY (type) REFERENCES person_type(id),
FOREIGN KEY (lang) REFERENCES language(code),
UNIQUE KEY (email)
);

DROP TABLE IF EXISTS job_type;
CREATE TABLE job_type
(
id INT,
description VARCHAR(200),
PRIMARY KEY(id)
);

DROP TABLE IF EXISTS industry;
CREATE TABLE industry
(
id INT,
name VARCHAR(200),
PRIMARY KEY(id)
);


DROP TABLE IF EXISTS job;
CREATE TABLE job
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
active TINYINT(1) DEFAULT 1,
PRIMARY KEY(id),
FOREIGN KEY(industry) REFERENCES industry(id),
FOREIGN KEY(job_type) REFERENCES job_type(id),
FOREIGN KEY(posted_by_person_id) REFERENCES person(id)
);


DROP TABLE IF EXISTS news;
CREATE TABLE news
(
id INT NOT NULL AUTO_INCREMENT,
title VARCHAR(256),
description TEXT(65535),
posted DATE,
expired DATE,
person_id INT,
views INT,
active CHAR(1) DEFAULT 'Y',
abuse CHAR(1) DEFAULT 'N',
PRIMARY KEY(id),
FOREIGN KEY(person_id) REFERENCES person(id)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment
(
id INT,
news_id INT,
comment_text TEXT(65535),
comment_timestamp DATE,
PRIMARY KEY(id),
FOREIGN KEY(news_id) REFERENCES news(id)
);

DROP TABLE IF EXISTS news_visibility_scope;
CREATE TABLE news_visibility_scope
(
id INT,
news_id INT,
PRIMARY KEY(id),
FOREIGN KEY(news_id) REFERENCES news(id)
);

DROP TABLE IF EXISTS social_service;
CREATE TABLE social_service
(
id VARCHAR(60) NOT NULL,
url VARCHAR(200),
PRIMARY KEY(id)
);

DROP TABLE IF EXISTS social_connection;
CREATE TABLE social_connection
(
id INT NOT NULL AUTO_INCREMENT,
person_id INT,
social_service_id VARCHAR(60),
url VARCHAR(200),
PRIMARY KEY(id),
FOREIGN KEY(person_id) REFERENCES person(id),
FOREIGN KEY(social_service_id) REFERENCES social_service(id)
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

