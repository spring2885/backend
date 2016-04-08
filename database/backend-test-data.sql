use backend;

/*Person*/
/* Dummy Template for Person
INSERT INTO person VALUES (
    id,
    name,
    student_id,
    title,
    about_me,
    resume_url,
    image_url,
    email,
    phone,
    occupation,
    company_name,
    birthdate,
    type,
    lang,
    last_logon,
    password,
    degree_major,
    degree_minor,
    graduation_year,
    degree_type,
    faculty_department,
    active);

*/
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
    'http://bkstr.scene7.com/is/image/Bkstr/862-CS2071-P2223929-Granite-Heather?$PDPMain$',
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
    1234,
    'Bruce Wayne',
    1,
    'CEO',
    'Im rich and my parents are dead.',
    null,
    'http://images2.fanpop.com/image/photos/9000000/Bruce-Wayne-the-dark-knight-9041631-367-500.jpg',
    'bruce@wayneenterprises.com',
    '6508984567',
    'Playboy',
    'Wayne Enterprises',
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
    'http://bkstr.scene7.com/is/image/Bkstr/862-CS2071-P2223929-Granite-Heather?$PDPMain$',
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
    
/*person - other examples*/
INSERT INTO person VALUES (
    5678,
    'Khan',
    2,
    'Human Augmented',
    'I am a superiod being from the 1990s that is better in every way.',
    'resume url',
    'http://www.wired.com/images_blogs/underwire/images/2009/01/14/st2khan.gif',
    'khan@reliant.net',
    null,
    'Evil Genius',
    'Genesis',
    null,
    1,
    'en',
    '2016-02-19',
    'password',
    null,
    null,
    null,
    null,
    null,
    0);
    
INSERT INTO person VALUES (
    6789,
    'Lando Calrissian',
    3,
    'General',
    'Have you ever been to cloud city? Yeah, thats me.',
    'resume url',
    'http://media.comicbook.com/wp-content/uploads/2012/11/lando-calrissian.jpg',
    'lando@heyubaby.net',
    null,
    'General',
    'Rebel Alliance',
    null,
    1,
    'en',
    '2016-02-19',
    'password',
    null,
    null,
    null,
    null,
    null,
    0);

INSERT INTO person VALUES (
    1945,
    'Steve Rogers',
    4,
    'Captain America',
    'Avengers Assemble!',
    null,
    'http://vignette3.wikia.nocookie.net/marvel-cinematic/images/3/32/Steve_Rogers_2.jpg/revision/latest?cb=20131025030358',
    null,
    null,
    'Captain',
    'The Avengers',
    null,
    0,
    'en',
    '2016-02-19',
    'password',
    null,
    null,
    null,
    null,
    null,
    1);
    
INSERT INTO person VALUES (
    30,
    'Stephen Curry',
    5,
    'Babyface Assassin/Chef Curry',
    'Shimmy',
    null,
    null,
    'warriors30@gsw.org',
    null,
    'Basketball Player',
    'Golden State Warriors',
    null,
    0,
    'en',
    '2016-02-19',
    'password',
    null,
    null,
    null,
    null,
    null,
    1);


/*person - student*/
INSERT INTO person VALUES (
    4,
    'student',
    000000,
    'student',
    'I am a student',
    'student resume',
    'student image',
    'student@ndnu.com',
    '6500000000',
    'Student',
    'NDNU',
    '2016-4-6',
    2,
    'en',
    '2016-4-6',
    'Student',
    null,
    null,
    null,
    null,
    null,
    1);

/*person - teacher*/
INSERT INTO person VALUES (
    5,
    'teacher',
    000002,
    'teacher',
    'I am a teacher',
    'teacher resume',
    'teacher image',
    'teacher@ndnu.com',
    '6500000002',
    'Teacher',
    'NDNU',
    '2016-4-6',
    2,
    'en',
    '2016-4-6',
    'teacher',
    null,
    null,
    null,
    null,
    null,
    1);

/*person - alumni*/
INSERT INTO person VALUES (
    6,
    'alumni',
    000001,
    'alumni',
    'I am a alumni',
    'alumni resume',
    'alumni image',
    'alumni@ndnu.com',
    '6500000001',
    'Alumni',
    'NDNU',
    '2016-4-6',
    1,
    'en',
    '2016-4-6',
    'alumni',
    null,
    null,
    null,
    null,
    null,
    1);

/*person - admin*/
INSERT INTO person VALUES (
    7,
    'admin',
    000003,
    'admin',
    'I am an admin',
    'admin resume',
    'admin image',
    'admin@ndnu.com',
    '6500000003',
    'Admin',
    'NDNU',
    '2016-4-6',
    2,
    'en',
    '2016-4-6',
    'admin',
    null,
    null,
    null,
    null,
    null,
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
    
INSERT INTO industry VALUES (
    3,
    'Other');
    
/*Job*/
/* Dummy template for jobs

INSERT INTO job VALUES (
    id,
    title,
    industry,
    location,
    description,
    job_type,
    start_date,
    end_date,
    posted_by_person_id,
    hours,
    active);
*/
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
    1);
    
INSERT INTO job VALUES (
    66334,
    'Hate the Empire? Click here.',
    3,
    'Endor Moon',
    'We hate the Empire too, but we also are doing something about it. We are looking for all types of people to come join the fight. Dont worry you dont have to be a jedi to help.',
    2,
    '2016-04-05',
    '2017-04-05',
    6789,
    null,
    1);

INSERT INTO job VALUES (
    7734,
    'Looking for temp work?',
    3,
    'Mutara Nebula',
    'Do you like to travel to far off places? Have adventure and work with driven smart, loyal people? Then this is the position for you. You must be a fast learner and willing to die.',
    2,
    '2016-04-07',
    '2017-04-07',
    5678,
    40,
    1);
    
INSERT INTO job VALUES (
    888222,
    'Looking for Planetary Science Expert',
    3,
    'Mutara Nebula',
    'We are looking for qualified people with a background in science and ideally planatery formation.  Must be able to take orders and be comfortable around a startship.',
    2,
    '2016-04-07',
    '2017-04-07',
    5678,
    40,
    1);
    
/*News Feed*/
/* Dummy Template for News
INSERT INTO news VALUES (
    id,
    title,
    description,
    posted,
    expired,
    person_id,
    views,
    active,
    abuse);
*/

INSERT INTO news VALUES (
    999999,
    'Im Drunk...',
    'I just wanted you to know. That is all.',
    '2016-02-18',
    null,
    1234,
    1,
    1,
    1);
    
INSERT INTO news VALUES (
    444444,
    'Who is this Batman',
    'He is a menace to us all. One cannot just take justice out of the hands of the law and bring it to the street in his pajamas.',
    '2016-02-12',
    null,
    1234,
    1,
    1,
    1);
    
INSERT INTO news VALUES (
    7878787,
    'Im looking for the guys that peed on my rug',
    'It really tied the room together',
    '2016-02-12',
    null,
    1945,
    1,
    1,
    1);
    
INSERT INTO news VALUES (
    9832,
    'Long post',
    'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
    '2016-02-18',
    null,
    6789,
    0,
    1,
    1);
    
INSERT INTO news VALUES (
    37892,
    'Looking for help',
    'Top secret, but I have a mission envoling a forest moon, teddy bears, and what rhymes with Seth Mahr...hit me back if you dont fear death
',
    '2016-02-18',
    null,
    6789,
    0,
    1,
    1);

/* News Comment*/
/* Dummy Template for News Comment
INSERT INTO news_comment VALUES (
    id,
    news_id,
    comment_text,
    comment_timestamp,
    person_id,
    active,
    abuse);
*/

INSERT INTO news_comment VALUES (
    01010101,
    999999,
    'Impressive, for a normal human.',
    '2016-02-18',
    5678,
    1,
    1);
    
INSERT INTO news_comment VALUES (
    01010102,
    444444,
    'Dont insult my intelligence',
    '2016-02-13',
    5678,
    1,
    1);

INSERT INTO news_comment VALUES (
    01010103,
    7878787,
    'The rug is not the issue here dude',
    '2016-02-13',
    5678,
    1,
    1);



