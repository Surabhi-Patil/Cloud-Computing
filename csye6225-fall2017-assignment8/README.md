Team member information

NAME and EMAIL
Surabhi Patil, 001251860, patil.sur@husky.neu.edu
Amitha_Murali, 001643826, murali.a@husky.neu.edu
Jyoti Sharma, 001643410, sharma.j@husky.neu.edu


Prerequisites for building and deploying your application locally.

* Linux environment
* Java 8
* IntelliJ
* Gradle 
* Apache Tomcat 8.5.20
* Jmeter for functional and load test

Build and Deploy instructions for web application.

* Open code in IntelliJ
* Setup Tomcat Local configuration and add war file to the deployment configuration
* Build War file
* Run the project
* Use either command line (Terminal) or Postman to test the endpoints.
 In terminal: 1) $ curl http://localhost:8080
			  2) $ curl -u user:password http://localhost:8080

Instructions to run unit, integration and/or load tests.
 ## To run the unit tests (created using Rest Assured framework) locally
 * Prereq: Application should be up and running.
 * Right click the test folder under the project src folder and select "Run all tests with coverage".

## Jmeter tests
* Create a test plan
* Add thread group
* Set the no of thread and user to 100
* Add HTTP requests in the thread group
* Run the test

Link to TravisCI build for the project.

* https://travis-ci.com/amithamurali/csye6225-fall2017
