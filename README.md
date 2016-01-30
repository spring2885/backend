# backend
Backend Repository for Spring2885 (REST + Server Side Java + Database)


## Overview
Make sure you have the most recent version of the source code in your repository.  Use ````git pull upstream master```` followed by a ````git push```` to keep your local copy (and your fork on github) up to date.

This application is using Apache Maven and the Spring Boot libraries to bootstrap itself.  Note the maven will pull down (and cache locally) *lots* of dependencies, so the first time you run each of these commands I'd recommend being on a network with fast internet *(i.e. not the university)*.

## Instructions

All of these instructions assume you are in the ````backend/server```` directory (where backend is the directory created by ````git clone```` for your local source).

You'll notice that these commands execute ````./mvnw```` instead of ````mvn```` directly.  
 
### Compiling the application
````./mvnw compile````

At the end you should see a success message such as:

````
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.945 s
[INFO] Finished at: 2016-01-30T10:44:15-08:00
[INFO] Final Memory: 16M/330M
[INFO] ------------------------------------------------------------------------

````

### Running 

To run the Web Application, we use the spring-boot plugin in maven. Here's the command to run the application.  It uses port ````8080```` by default.  Access it through your browser using: ````http://localhost:8080/````.

````
./mvnw spring-boot:run
````

On a successful run, you'll have a ton of log messages, and about 1-2 pages up you'll find the default password for the server.

````
Using default security password: a24db8e6-a268-4ef3-8f4b-57f39b8b4134

...

2016-01-30 10:54:06.117  INFO 92184 --- [           main] org.spring2885.server.ServerApplication  : Started ServerApplication in 3.989 seconds (JVM running for 6.143)

````

Using the password, you can log using the username: ````user```` and the password displayed in your terminal window. 

### Running Tests

Use the maven test command to execute all of the server-side JUnit tests.  Here's the command:

````
./mvnw test
````

A successful test run should look similar to this:

````
Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 5.266 s
[INFO] Finished at: 2016-01-30T10:57:45-08:00
[INFO] Final Memory: 17M/332M
[INFO] ------------------------------------------------------------------------
````
