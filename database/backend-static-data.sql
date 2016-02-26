# Script to create data needed by the system
# (social connections, types, etc)

USE BACKEND;

/*Already in DB*/
INSERT INTO Person_Type VALUES (
    0,
    'student');

INSERT INTO Person_Type VALUES (
    1,
    'alumni');
    
INSERT INTO Person_Type VALUES (
    2,
    'faculty');
    
INSERT INTO Job_Type VALUES (
    1,
    "full-time");

INSERT INTO Job_Type VALUES (
    2,
    "part-time");

INSERT INTO Social_Service VALUES(
	'LinkedIn', 'http://www.linkedin.com/');
INSERT INTO Social_Service VALUES(
	'Twitter', 'http://www.twitter.com/');
INSERT INTO Social_Service VALUES(
	'Facebook', 'http://www.facebook.com/');

