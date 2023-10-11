# password_meter
This is a practical test for candidate evaluation at NETDEAL implementing a backend REST API with Spring Framework and a frontend client with AngularJS

Enclosed in this project, lies a small Java application that expose a 
REST service that returns a list of Users.

There is also a AngularJS application to consume the REST Service and list the results in a table
This is a CRUD exemple capable of persist a User with a Self-Reference "Parent User" or "Hierarchy".
You can also see a Bussiness Rule for Password Strength score and status, completely developed in the backend as a service.

# Installation Guide

# Requirements:

	1) Java JDK 11 and Maven previously installed
	2) Git, Node, Bower, and Grunt installed and configured
	
# Download and extract the zip file package at 

    https://github.com/eduflores/password_meter
    
# or by command using

    git clone https://github.com/eduflores/password_meter

# run this command in the root folder of the project

    npm install
    bower install
    
# this command is optional. It will minify the js files.

    grunt default
    
# After you sucessfully installed everything, just open a command and run:

    mvn clean install
    
# To run, either call Java or run Maven

    java -jar target/password_meter.jar
    
or

    mvn spring-boot:run
    
# This service can be reached using the following URL:

    http://localhost:8080/api/users
    
# The application can be seen using the following URL:

    http://localhost:9090

# Current endpoints:

# Get All Users: 

curl --location 'http://localhost:8080/api/users'

# Get Password Strength: 

//The last paremeter is a PathVariable and must be ENCODED in Base64
curl --location 'http://localhost:8080/api/pwd-str/MTIzNDU2'

# Create User: 

//You can create a user without the "parentUser" field
curl --location 'http://localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Eduardo",
    "password": "MTIzNDU2"
}'

//You can create a user with the "parentUser" field
curl --location 'http://localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Leonardo",
    "password": "MTIzNDU2",
    "parentUser": 1
}'
