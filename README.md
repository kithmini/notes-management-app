# notes-management-app

This repo contains a simple notes management application providing a REST
API written using Spring Boot and MySQL DB.

You can directly test the application by downloading the following Postman collection.

https://www.getpostman.com/collections/1bf460c5fbea480302ec

The application is live at http://18.191.16.11:9094/notes

(Resource URLs & credentials can be found in the Postman collection)  


The application is capable of running in a `multi-user envronment` with the concurrency management provided by Hibernate.  
As of now basic authentication is provided for 2 in memory  users "admin" & "user" via Spring WebSecurityConfigurerAdapater. 
This can be extended via a user management service.
 
## Prerequisites

MySQL - Installation instructions https://dev.mysql.com/doc/mysql-installation-excerpt/8.0/en/


Clone the repository and modify the following parameters in the `application.yml` file providing values from your MySQL installation.

spring:datasource:url :
spring:datasource:username :
spring:datasource:password :

Keep trailing parameters of URL `useSSL=false&allowPublicKeyRetrieval=true` intact

Run the `dbinit.sql` with your MySQL installation.


## Install

	mvn clean install

## Run the app

	nohup java -jar notes-app-0.0.1-SNAPSHOT.jar &
	
	tail -f nohup.out

## Run the tests

	mvn test

# REST API

The REST API to the notes management application is described below.

## Get list of notes (unarchived)

### Request

`GET /notes`

	curl --location --request GET '18.191.16.11:9094/notes' \
	--header 'Content-Type: application/json' \
	--header 'Authorization: Basic YWRtaW46YWRtaW4xMjM='

### Response

	HTTP/1.1 200 OK
	Date: Tue, 22 Sep 2020 14:01:52 GMT
	Status: 200 OK
	Transfer-Encoding: chunked
	Keep-Alive: timeout=60
	Connection: keep-alive
	Content-Type: application/json

	[
		{
			"id": 2,
			"title": "testnote 2",
			"content": "This is a test note 2 with random text",
			"createdBy": "admin",
			"lastModifiedBy": null,
			"createdDate": "2020-09-22T08:57:58.392+00:00",
			"lastModifiedDate": null
		},
		{
			"id": 3,
        		"title": "testnote 3",
        		"content": "This is a test note 3 with random text",
        		"createdBy": "admin",
        		"lastModifiedBy": null,
        		"createdDate": "2020-09-22T08:58:11.794+00:00",
        		"lastModifiedDate": null
    		}
	]

## Create a new note

### Request

`POST /notes`

	curl --location --request POST '18.191.16.11:9094/notes' \
	--header 'Authorization: Basic YWRtaW46YWRtaW4xMjM=' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "title":"testnote 10",
	    "content":"This is a test note 10 with random text"
	}'
### Response

	HTTP/1.1 201 Created
	Date: Tue, 22 Sep 2020 14:01:52 GMT
	Status: 201 Created
	Transfer-Encoding: chunked
	Keep-Alive: timeout=60
	Connection: keep-alive
	Content-Type: application/json

	{
		"id": 10,
		"title": "testnote 10",
		"content": "This is a test note 10 with random text",
		"createdBy": "admin",
		"lastModifiedBy": null,
		"createdDate": "2020-09-22T13:54:38.237+00:00",
		"lastModifiedDate": null
	}

## Update a note

### Request

`PUT /notes/{id}`

	curl --location --request PUT '18.191.16.11:9094/notes/11' \
	--header 'Authorization: Basic dXNlcjp1c2VyMTIz' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "title":"testnote 11",
	    "content":"This is an updated test note 11 with random text"
	}'

### Response

	HTTP/1.1 200 OK
	Date: Tue, 22 Sep 2020 14:01:52 GMT
	Status: 200 OK
	Transfer-Encoding: chunked
	Keep-Alive: timeout=60
	Connection: keep-alive
	Content-Type: application/json

	{
		"id": 11,
		"title": "testnote 11",
		"content": "This is an updated test note 11 with random text",
		"createdBy": "admin",
		"lastModifiedBy": "user",
		"createdDate": "2020-09-22T15:21:46.180+00:00",
		"lastModifiedDate": "2020-09-22T15:22:12.853+00:00"
	}

#### If the note is non-existent
   
	HTTP/1.1 500 Internal Server Error
	Date: Tue, 22 Sep 2020 14:01:52 GMT
	Status: 500 Internal Server Error
	Connection: close

	Note not found for id : 255


## Delete a note

### Request

`DELETE /notes/{id}`

	curl --location --request DELETE '18.191.16.11:9094/notes/8' \
	--header 'Authorization: Basic YWRtaW46YWRtaW4xMjM='

### Response

	HTTP/1.1 204 No Content
	Date: Tue, 22 Sep 2020 14:01:52 GMT
	Status: 204 No Content
	Keep-Alive: timeout=60
	Connection: keep-alive


#### If the note is non-existent
   
    	HTTP/1.1 500 Internal Server Error
    	Date: Tue, 22 Sep 2020 14:01:52 GMT
    	Status: 500 Internal Server Error
    	Connection: close

	No class com.bkitsen.notesapp.persistence.entity.Note entity with id 255 exists!


## Get a list of archived notes

### Request

`GET /archived-notes`

	curl --location --request GET '18.191.16.11:9094/archived-notes' \
	--header 'Authorization: Basic dXNlcjp1c2VyMTIz'

### Response

	HTTP/1.1 200 OK
	Date: Tue, 22 Sep 2020 14:01:52 GMT
	Status: 200 OK
	Transfer-Encoding: chunked
	Keep-Alive: timeout=60
	Connection: keep-alive
	Content-Type: application/json

	[
		{
			"id": 4,
			"title": "testnote 4",
			"content": "This is a test note 4 with random text",
			"createdBy": "admin",
			"lastModifiedBy": "user",
			"originalCreatedDate": "2020-09-22T08:58:22.025+00:00",
			"archivedDate": "2020-09-22T13:58:36.851+00:00"
		},
		{
			"id": 5,
			"title": "testnote 5",
			"content": "This is a test note 5 with random text",
			"createdBy": "admin",
			"lastModifiedBy": "user",
			"originalCreatedDate": "2020-09-22T13:52:34.760+00:00",
			"archivedDate": "2020-09-22T14:00:05.440+00:00"
		}
	]

## Archive a note

### Request

`PUT /archived-notes/{id}/archive`

	curl --location --request PUT '18.191.16.11:9094/archived-notes/5/archive' \
	--header 'Authorization: Basic dXNlcjp1c2VyMTIz'

### Response

	HTTP/1.1 200 OK
	Date: Tue, 22 Sep 2020 14:01:52 GMT
	Status: 200 OK
	Transfer-Encoding: chunked
	Keep-Alive: timeout=60
	Connection: keep-alive
	Content-Type: application/json

	{
		"id": 5,
		"title": "testnote 5",
		"content": "This is a test note 5 with random text",
		"createdBy": "admin",
		"lastModifiedBy": "user",
		"originalCreatedDate": "2020-09-22T13:52:34.760+00:00",
		"archivedDate": "2020-09-22T14:00:05.440+00:00"
	}

## Unarchive an archived note

### Request

`GET /archived-notes/{id}/unarchive`

	curl --location --request PUT '18.191.16.11:9094/archived-notes/5/unarchive' \
	--header 'Authorization: Basic YWRtaW46YWRtaW4xMjM='

### Response

	HTTP/1.1 200 OK
	Date: Tue, 22 Sep 2020 14:01:52 GMT
	Status: 200 OK
	Transfer-Encoding: chunked
	Keep-Alive: timeout=60
	Connection: keep-alive
	Content-Type: application/json

	{
	    "id": 11,
	    "title": "testnote 5",
	    "content": "This is a test note 5 with random text",
	    "createdBy": "admin",
	    "lastModifiedBy": "admin",
	    "createdDate": "2020-09-22T13:52:34.760+00:00",
	    "lastModifiedDate": "2020-09-22T14:00:27.379+00:00"
	}

# Choice of Technology

### Reasons for using Spring Boot (Java)

	* Fast development time due to relatively easier configuration
	* JPA with Hibernate providing transactional management capabilities which are important for a multi user system
	  (These are available out of the box unlike in certain other languages)
	* Has a strong security framework which is important to secure the application.

### Reasons for using MySQL
	
	* It is a stable, reliable, powerful, solution

### Alternatives considered

	* I considered using Golang for development. Lack of a proper ORM was a negative point in my opinion. Security implementaion and concurrency handling was accessed to be more complex with Go compared with Java Spring Boot.
	* I considered selecting a NoSQL DB as well. While NoSQL has its benefits for a document management system I chose MySQL for it's conveince. If we can consider this to be a transaction oriented system (accordng to current behaviour) and since there is no obstacle to proceed with structured data a relational DB could provide better performance altogeter.


# Further Improvements

Enhancing the authentication with a user management system

Provide better error handling and error codes implementation

Introduce containerization for a more convenient deployment (half-way done)

