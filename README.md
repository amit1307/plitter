# plitter
HSBC social networking app coding exercise

A web API for HSBC coding exercise. It has the following functionality:

<b> Posting </b>
A user should be able to post a 140 character message.

<b> Wall </b>
A user should be able to see a list of the messages they've posted, in reverse chronological order.

<b> Following </b>
A user should be able to follow another user. Following doesn't have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.

<b> Timeline </b>
A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.

It is a spring boot application that can be started up by executing the below application class:

<b> PlitterApplication </b>

# Application End Points

<b> Posting </b>

Post : message by the logged in user : http://localhost:8080/messages

Get : a message by message id the logged in user : http://localhost:8080/messages/123

<b> Wall </b>

Get : all the messages the logged in user : http://localhost:8080/messages/getAll

<b> Following </b>

Post : logged in user follow another user : http://localhost:8080/follow?userName=user1

Get : all followed users for the logged in user : http://localhost:8080/follow/

Get : all followed users for the given user : http://localhost:8080/follow/bob

<b> Timeline </b>

Get : logged in user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order : http://localhost:8080/timeline

<b> User Service </b>

Post : set logged in user : http://localhost:8080/userService?setLoggedInUser=User2

Get : currently logged in user : http://localhost:8080/userService/loggedIn

Get : all registered users : http://localhost:8080/userService

# Swagger API Documentation

Swagger API documentation can be accessed at:

http://localhost:8080/swagger-ui.html

Please note this code is not production ready, it is purely for the exercise purpose and needs lots of changes for production readiness.


