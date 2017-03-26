DROP DATABASE IF EXISTS backend;

CREATE DATABASE IF NOT EXISTS backend;

USE backend;

DROP TABLE IF EXISTS person_type;
CREATE TABLE person_type
(
    id INT AUTO_INCREMENT,
    name VARCHAR(100),
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS language;
CREATE TABLE language
(
    #IANA RFC 3066 language tags (ka ISO-639 + ISO-3166)
    code VARCHAR(20) NOT NULL,
    description VARCHAR(80) NOT NULL,
    PRIMARY KEY(code),
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100)
);

# person is a union of the base person plus
# student, alumni, faculty
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
    occupation VARCHAR(200),
    company_name VARCHAR(200),
    type INT,
    lang VARCHAR(20),
    last_logon DATETIME,
    password VARCHAR(255),
    #student only fields
    degree_major VARCHAR(200),
    degree_minor VARCHAR(200),
    #student/alumni shared fields
    graduation_year INT,
    degree_type VARCHAR(200),
    #faculty field
    faculty_department VARCHAR(200),
    active TINYINT(1) DEFAULT 1,
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    PRIMARY KEY (id),
    FOREIGN KEY (type) REFERENCES person_type(id),
    FOREIGN KEY (lang) REFERENCES language(code),
    UNIQUE KEY (email)
);

DROP TABLE IF EXISTS job_type;
CREATE TABLE job_type
(
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(200),
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS industry;
CREATE TABLE industry
(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(200),
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
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
    company VARCHAR(200),
    job_type INT,
    start_date DATETIME,
    end_date DATETIME,
    posted_by_person_id INT,
    hours INT,
    active TINYINT(1) DEFAULT 1,
    abuse TINYINT(1) DEFAULT 0,
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
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
    posted DATETIME,
    expired DATETIME,
    person_id INT,
    views INT,
    active TINYINT(1) DEFAULT 1,
    abuse TINYINT(1) DEFAULT 0,
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY(person_id) REFERENCES person(id)
);

DROP TABLE IF EXISTS news_visibility;
CREATE TABLE news_visibility
(
    person_type INT,
    news INT,
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    PRIMARY KEY(person_type, news),
    FOREIGN KEY(person_type) REFERENCES person_type(id),
    FOREIGN KEY(news) REFERENCES news(id)
);

DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS news_comment;
CREATE TABLE news_comment
(
    id INT NOT NULL AUTO_INCREMENT,
    news_id INT,
    comment_text TEXT(65535),
    comment_timestamp DATETIME,
    person_id INT,
    active TINYINT(1) DEFAULT 1,
    abuse TINYINT(1) DEFAULT 0,
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY(news_id) REFERENCES news(id),
    FOREIGN KEY(person_id) REFERENCES person(id)
);

DROP TABLE IF EXISTS social_service;
CREATE TABLE social_service
(
    id VARCHAR(60) NOT NULL,
    url VARCHAR(200),
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS social_connection;
CREATE TABLE social_connection
(
    id INT NOT NULL AUTO_INCREMENT,
    person_id INT,
    social_service_id VARCHAR(60),
    url VARCHAR(200),
    #Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY(person_id) REFERENCES person(id),
    FOREIGN KEY(social_service_id) REFERENCES social_service(id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id INT NOT NULL,
    rolename VARCHAR(60),
    PRIMARY KEY(id, rolename)
);

DROP TABLE IF EXISTS token;
CREATE TABLE token
(
    uuid VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    date_created DATETIME,
#   Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,

    PRIMARY KEY(uuid)
);

DROP TABLE IF EXISTS approval_request;
CREATE TABLE approval_request
(
    uuid VARCHAR(200) NOT NULL,
    active TINYINT(1) DEFAULT 1,
    approval_type VARCHAR(60) NOT NULL,
    item_type VARCHAR(60) NOT NULL,
    item_id INT NOT NULL,
    item_url VARCHAR(255),
    # Creation info.
    flagged_on DATETIME,
    flagged_by INT,
    flagged_notes TEXT(30000),
    # Verdict info.
    approved TINYINT(1) DEFAULT 0,
    verdict_on DATETIME,
    verdict_by INT,
    verdict_notes TEXT(30000),
    # Auditing Columns
    version INT,
    creation_time DATETIME,
    modification_time DATETIME,
    PRIMARY KEY(uuid),
    FOREIGN KEY(flagged_by) REFERENCES person(id),
    FOREIGN KEY(verdict_by) REFERENCES person(id)
);

CREATE TABLE templates
(
    id VARCHAR(64) NOT NULL PRIMARY KEY, 
    body TEXT
);

--
--  Static data required for all instances.
-- 
# Script to create data needed by the system
# (social connections, types, etc)
USE backend;

/*Already in DB*/
INSERT INTO person_type VALUES (
    1,
    'student',
    1,now(),
    now()
);

INSERT INTO person_type VALUES (
    2,
    'alumni',
    1,
    now(),
    now()
);

INSERT INTO person_type VALUES (
    3,
    'faculty',
    1,
    now(),
    now()
);

INSERT INTO job_type VALUES (
    1,
    "full-time",
    1,
    now(),
    now(),
    NULL,
    NULL
);

INSERT INTO job_type VALUES (
    2,
    "part-time",
    1,
    now(),
    now(),
    NULL,
    NULL
);

INSERT INTO social_service VALUES('Angellist', 'https://angel.co/',1,now(), now());
INSERT INTO social_service VALUES('Bitbucket', 'https://bitbucket.org/',1,now(), now());
INSERT INTO social_service VALUES('Facebook', 'http://www.facebook.com/',1,now(), now());
INSERT INTO social_service VALUES('Flickr', 'https://www.flickr.com/',1,now(), now());
INSERT INTO social_service VALUES('Foursquare', 'https://foursquare.com/',1,now(), now());
INSERT INTO social_service VALUES('github', 'https://github.com/',1,now(), now());
INSERT INTO social_service VALUES('Googleplus', 'https://plus.google.com/',1,now(), now());
INSERT INTO social_service VALUES('Instagram', 'https://www.instagram.com/',1,now(), now());
INSERT INTO social_service VALUES('LinkedIn', 'http://www.linkedin.com/',1,now(), now());
INSERT INTO social_service VALUES('Medium', 'https://medium.com/',1,now(), now());
INSERT INTO social_service VALUES('Pinterest', 'https://www.pinterest.com/',1,now(), now());
INSERT INTO social_service VALUES('Quora', 'https://www.quora.com/',1,now(), now());
INSERT INTO social_service VALUES('Scribd', 'https://www.scribd.com/',1,now(), now());
INSERT INTO social_service VALUES('Slideshare', 'http://www.slideshare.net/',1,now(), now());
INSERT INTO social_service VALUES('Spotify', 'https://www.spotify.com/us/',1,now(), now());
INSERT INTO social_service VALUES('Tumblr', 'https://www.tumblr.com/',1,now(), now());
INSERT INTO social_service VALUES('Twitch', 'https://www.twitch.tv/',1,now(), now());
INSERT INTO social_service VALUES('Twitter', 'http://www.twitter.com/',1,now(), now());
INSERT INTO social_service VALUES('Yelp', 'http://www.yelp.com/',1,now(), now());
INSERT INTO social_service VALUES('Youtube', 'https://www.youtube.com/',1,now(), now());

INSERT INTO language VALUES('ar', 'Arabic',1,now(), now(), NULL, NULL);
INSERT INTO language VALUES('en', 'English',1,now(), now(), NULL, NULL);
INSERT INTO language VALUES('es', 'Spanish',1,now(), now(), NULL, NULL);
INSERT INTO language VALUES('fr', 'French',1,now(), now(), NULL, NULL);
INSERT INTO language VALUES('zh', 'Chinese',1,now(), now(), NULL, NULL);

INSERT INTO templates VALUES('forgot', 
'You recently requested to reset your {{ app_name }} password. 
To complete this process, please click the following link:
{{ reset_url }} 

If you did not initiate this request, please disregard. 

This is an automatically generated message. PLEASE DO NOT REPLY TO THIS E-MAIL. 

Thank you, 
{{ from_name }}'
);