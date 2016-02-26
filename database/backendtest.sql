USE backend;

    
/*Person*/  
/*Student*/
INSERT into person values (
    1,
    'Matthew Tecson',
    475000,
    'Student',
    'about me',
    'resume url',
    'image url',
    'matt@spring2885.org',
    '6504831355',
    'full time student',
    'NDNU CompSci',
    '1994-06-26',
    1,
    '2016-02-19',
    'mattpw');
    
INSERT into person values (
    4,
    'Curtis Yokoyama',
    123000,
    'Student',
    'about curtis',
    'curtis resume',
    'curtis image url',
    'curtis@spring2885.org',
    '6503713377',
    'full time student',
    'NDNU Compsci',
    '1995-02-20',
    1,
    '2016-1-30',
    'curtispw');
    
INSERT into person values (
    3,
    'Jen',
    461000,
    'Student',
    'about jen',
    'jen resume',
    'jen image url',
    'jen@spring2885.org',
    '6503921234',
    'full time student',
    'NDNU Compsci',
    '1994-05-23',
    1,
    '2016-1-26',
    'jenpw');
    
INSERT into person values (
    2,
    'Rob',
    432000,
    'Student',
    'about rob',
    'rob resume',
    'rob image url',
    'rob@spring2885.org',
    '6501231234',
    'full time student',
    'NDNU Compsci',
    '1980-05-23',
    1,
    '2016-2-15',
    'robpw');

/*Faculty*/
INSERT into person values (
    101,
    'John Youssefi',
    0,
    'Professor',
    'about Youssefi',
    'Youssefi resume',
    'Youssefi image url',
    'youssefi@spring2885.org',
    '6509998888',
    'Direcrot of Computer Science',
    'NDNU Compsci',
    '1970-05-23',
    2,
    '2015-12-24',
    'youssefipw');
    
INSERT into person values (
    102,
    'Emese Bari',
    0,
    'Professor',
    'about Bari',
    'Bari resume',
    'Bari image url',
    'bari@spring2885.org',
    '6509876654',
    'Professor',
    'Ebay',
    '1980-05-23',
    2,
    '2016-1-10',
    'baripw');
    
/*Alumni*/
INSERT into person values (
    42,
    'Luke Skywalker',
    198544,
    'Jedi Master PhD',
    'about Luke',
    'Luke resume',
    'Luke image url',
    'luke@spring2885.org',
    '5501231245',
    'Jedi Master',
    'Jedi Order',
    '1970-05-23',
    3,
    '2015-12-30',
    'lukepw');
    
INSERT into person values (
    11,
    'Kylo Ren',
    111111,
    'Sith Apprentice',
    'about Kylo',
    'Kylo resume',
    'Kylo image url',
    'kyloe@spring2885.org',
    '1112223333',
    'Sith Apprentice',
    'New Order',
    '2015-12-11',
    3,
    '2015-12-12',
    'kylopw');
    
/*News*/
INSERT into news values (
    1,
    'Rise of the New Order',
    'The New Order Rise. Return of the sith!',
    '2016-02-19',
    '2016-06-19',
    11,
    2);
    
INSERT into news values (
    2,
    'Rise of the Jedi Order',
    'Luke found his lightsaber',
    '2016-02-19',
    '2016-07-19',
    42,
    20);
    
INSERT into news values (
    3,
    'Senior Project is on its Way!',
    'Comp Sci Students on working on their project',
    '2016-02-19',
    '2016-07-19',
    101,
    1);

INSERT into industry values (
    1,
    'Technology');
    
/*Jobs*/
INSERT into job values (
    1,
    'Turn I.T',
    1,
    'Redwood City',
    'IT Intern',
    1,
    '2016-02-19',
    '2016-05-07',
    101,
    20);
    
INSERT into job values (
    2,
    'Gentech I.T',
    1,
    'South San Francisco',
    'IT Intern',
    1,
    '2016-02-19',
    '2016-05-07',
    102,
    20);
    
/*Alumni*/
INSERT into Alumni values (
    42,
    42,
    2005,
    'Computer Science');
    
INSERT into Alumni values (
    11,
    11,
    2010,
    'Kinesiology');

/*Faculty - 2 count*/
INSERT into faculty values (
    101,
    101,
    'Computer Science');
    
INSERT into faculty values (
    102,
    102,
    'Computer Science');

/*Current_Student - 4 count*/
INSERT into Current_Student values (
    2,
    2,
    'Biology',
    null,
    'Fall 2016');
    
INSERT into Current_Student values (
    3,
    3,
    'Computer Science',
    null,
    'May 2016');
    
INSERT into Current_Student values (
    4,
    4,
    'Business',
    'Math',
    'Spring 2018');
    
INSERT into Current_Student values (
    1,
    1,
    'Computer Science',
    null,
    'Spring 2016');

    

