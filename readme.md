# REST API Elotech selection process

## Backend documentation : [Docs API](https://github.com/ViniciusMVilela/elotechSeletive/blob/main/backend/readme.md)

## Tools used

Java 17, Spring Boot 3, Angular, Postgresql

## Warnings

#### backendsrc/main/resources/application-prod.properties

spring.datasource.password=postgres

change password "postgress" to your pgAdmin password

create a database postgres with name "elotechCrudProcess"

#### Initialization

Frontend and backend run separately, to use the entire application you need to start the backend and then start the frontend:

to run the backend start "CrudApplication"

to run the frontend, access the frontend folder and use the "ng serve" command


#### After initialization
use the sidebar icons to navegate the application

#### main routes

"http://localhost:4200/person-list" 
load ALL people present into database  


"http://localhost:4200/person-form"
forms for creating a person


"http://localhost:4200/home"
homepage 