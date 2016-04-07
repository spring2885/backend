use backend;

/*Person*/
/*Student*/
INSERT INTO person VALUES (
    1,
    'Matthew Tecson',
    123456,
    'Student',
    'My name is Matthew but people call me Matt',
    'http://jasonczaja.com/blog/wp-content/uploads/2009/01/jason_czaja_resume-791x1024.png',
    'https://pbs.twimg.com/profile_images/1779612438/tumblr_lwgg1cLBn21qbg0ryo1_500_400x400.jpg',
    'matt@ndnu.edu',
    '6501112222',
    'instructor',
    'TechRocks',
    '1994-06-26',
    0,
    'en',
    '2016-02-19',
    'password',
    'Computer Science',
    null,
    2016,
    'BS',
    null,
    1);
    
/*Alumni*/
INSERT INTO person VALUES (
    3,
    'Aaron Cruz',
    789123,
    'Alumni',
    'My name is Aaron Cruz and I work at Aurora',
    'cruz resume',
    '/src/assets/images/sweatshirt-full.jpg',
    'cruz@example.com',
    '6508984567',
    'Aurora Software Engineer',
    'Aurora',
    '2016-02-19',
    1,
    'en',
    '2016-02-19',
    'passwordCruz',
    'Computer Science',
    null,
    2014,
    'BS',
    null,
    1);
    
INSERT INTO person VALUES (
    4,
    'Bruce Wayne',
    789123,
    'Alumni',
    'My name is Bruce Wayne and I am not Batman',
    'Bruce Wayne resume',
    'http://images2.fanpop.com/image/photos/9000000/Bruce-Wayne-the-dark-knight-9041631-367-500.jpg',
    'wayne@example.com',
    '6508984567',
    'Not a vigilante',
    'Justice League',
    '2016-02-19',
    1,
    'en',
    '2016-02-19',
    'passwordBatman',
    'PhD in Vigilante',
    null,
    1960,
    'PhD',
    null,
    1);

/*Faculty*/
INSERT INTO person VALUES (
    2,
    'John Youssefi',
    null,
    'Director of Computer Science, PhD',
    'My name is John Youssefi, all you need is 2 lines of code to succeed!',
    'youssefi resume',
    '/src/assets/images/sweatshirt-full.jpg',
    'jyoussefi@ndnu.edu',
    '6502223333',
    'Professor, Advisor',
    'Notre Dame De Namur',
    '2016-02-19',
    2,
    'en',
    '2016-02-19',
    'passwordJY',
    null,
    null,
    null,
    null,
    'Computer and Information Science',
    1);
    
/*News*/
INSERT INTO news VALUES (
    1,
    'NDNU : Looking for Intern',
    'We at NDNU, are looking for a software Engineer Intern',
    '2016-04-01',
    '2017-04-01',
    2,
    1,
    1,
    0);
    
/*News Comment*/
INSERT INTO news_comment VALUES(
    1,
    1,
    'This is a job I would like to apply for!',
    '2016-04-01',
    1,
    1,
    0);
    
INSERT INTO news_comment VALUES(
    2,
    1,
    'How can I forward my resume?',
    '2016-04-01',
    3,
    1,
    0);

/*News Visibility Scope*/
INSERT INTO news_visibility VALUES (
    0, 
    1);

INSERT INTO news_visibility VALUES (
    1,
    1);

/*Industry*/
INSERT INTO industry VALUES (
    1,
    'Tech Industry');
    
INSERT INTO industry VALUES (
    2,
    'Vigilante');
    
/*Job*/
INSERT INTO job VALUES (
    1,
    'IT Intern',
    1,
    'Redwood City',
    'This position is for an IT Intern. Problem? Tell them to restart their computer',
    2,
    '2016-04-01',
    '2017-04-01',
    2,
    20,
    1);
    
INSERT INTO job VALUES (
    2,
    'Sidekick',
    1,
    'Batcave',
    'You will be Batmans sidekick. You will help beat the bad guys.',
    1,
    '2016-04-01',
    '2017-04-01',
    4,
    40,
    1)
