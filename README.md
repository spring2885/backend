# backend
Backend Repository for Spring2885 (REST + Server Side Java + Database)


## Overview
Make sure you have the most recent version of the source code in your repository.  Use ````git pull upstream master```` followed by a ````git push```` to keep your local copy (and your fork on github) up to date.

This application is using Apache Maven and the Spring Boot libraries to bootstrap itself.  Note the maven will pull down (and cache locally) *lots* of dependencies, so the first time you run each of these commands I'd recommend being on a network with fast internet *(i.e. not the university)*.

## Eclipse IDE Instructions

Make sure you have the latest Eclipse installed, such as the Mars release (4.5.1).  Also you must install m2e plugins.  See [M2Eclipse: Setting up Eclipse IDE](http://www.eclipse.org/m2e/documentation/m2e-extension-development.html) for instructions.

### Creating a workspace

The projects are shared in the Git repository, however Eclipse workspaces are not a sharable artifact.  Create a new workspace at the same level as the ```backend``` repository.

Example:

![Select workspace](./docs/select-workspace.png)

### Import a Project

The first time after you create a workspace, you must import the existing Maven project (pom.xml) into the workspace as an Eclipse Project.

Follow along with how I imported the ```server``` (and only) project into the ````backend```` workspace.

![Import Dialog](./docs/import-dialog.png)

![Select root directory](./docs/root-directory.png)

![Import Project](./docs/import-projects.png)


### Setting up Eclipse Web Server 

You can use any web server that the WTP supports, however I'm using *Tomcat 8.0* since it's the latest Tomcat supported by Eclipse 4.5.1.

Download tomcat from http://tomcat.apache.org and unzip it somewhere.  I put mine in the home directory, i.e. ````/Users/rob/````

You will now need to add a J2EE server into Eclipse, and also make sure your backend project is installed on it.


### Compile, Run, Debug

You should be able to Compile, Run and Debug on the Server now just like any other web application in Eclipse.


## Command Line Instructions

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
