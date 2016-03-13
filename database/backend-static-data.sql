# Script to create data needed by the system
# (social connections, types, etc)

USE backend;

/*Already in DB*/
INSERT INTO person_type VALUES (
    0,
    'student');

INSERT INTO person_type VALUES (
    1,
    'alumni');
    
INSERT INTO person_type VALUES (
    2,
    'faculty');
    
INSERT INTO job_type VALUES (
    1,
    "full-time");

INSERT INTO job_type VALUES (
    2,
    "part-time");

INSERT INTO social_service VALUES(
	'LinkedIn', 'http://www.linkedin.com/');
INSERT INTO social_service VALUES(
	'Twitter', 'http://www.twitter.com/');
INSERT INTO social_service VALUES(
	'Facebook', 'http://www.facebook.com/');

INSERT INTO language VALUES('en', 'English');

