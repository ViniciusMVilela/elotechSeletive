# REST API Elotech selection process
This API is used to manipulate objects of type Person and their data

## Endpoints
### GET /person?name=
This endpoint is responsible for returning a pageable page with all registered person's that contain the name
#### Parameters
?name=: name filter to search for person who contain "name" in their name data

#### Server responses
##### OK! 200

If this response happens, you will receive the page with the person containing the "name"

Response example:/person?name=Per
```
{
    "content": [
        {
            "id": 1,
            "name": "Person name",
            "cpf": "0000000000",
            "birthDate": "1971-07-21",
            "contactList": [
                {
                    "id": 1,
                    "nameContact": "person telephone",
                    "telephone": "4499999999",
                    "email": "person@gmail.com"
                }
            ]
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 20,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
```
### GET /person/id
This endpoint is responsible for returning a person with the respective id
#### Parameters
id: person id

#### Server responses
##### OK! 200

If this response happens, you will receive the person who owns the id

Response example: /person/2
```
{
    "id": 2,
    "name": "person name",
    "cpf": "86906272004",
    "birthDate": "1971-07-21",
    "contactList": [
        {
            "id": 2,
            "nameContact": "person telephone",
            "telephone": "44998007033",
            "email": "person@gmail.com"
        }
    ]
}
```

##### Not Found 404

This response happens when an error occurs when returning the person.

Reason: Entity with this id is not registered in the database

Example: 
```` 
Entity not found
````

### POST /person
Endpoint responsible for creating a person in the database
#### Parameters
#### all data must be non-null and not empty 
Body:

name: person name

cpf: person cpf

birthDate: person birth date (yy-mm-dd)

contactList: person contact list

Example:
```
{
    "name": "person name",
    "cpf": "86906272004",
    "birthDate": "1971-07-21",
    "contactList": [
        {
            "nameContact": "person telephone",
            "telephone": "44998007033",
            "email": "person@gmail.com"
        }
    ]
}
```

#### Server responses
##### Created 201

It means that the person was created correctly

##### Bad Request 400

This response occurs when an error occurs in the request process.

Reason: 

1. some of the fields may be empty/null 

2. cpf already registered in the database

Example 1:
for a empty contact list
```
Validation Error: Property: contactList, Message: não deve estar vazio; 
```
Example 2:
```
Please confirm fields, datas can already exists
```

### PUT /id
Endpoint responsible for updating a person in the database

#### Parameters
id: person id

Body: person datas


Example: /person/2
```
{
    "name": "person name",
    "cpf": "86906272004",
    "birthDate": "1971-07-21",
    "contactList": [
        {
            "nameContact": "person telephone",
            "telephone": "44998007033",
            "email": "person@gmail.com"
        }
    ]
}
```

#### Server responses
##### OK 200

It means that the person was updated correctly

##### Bad Request 400

This response occurs when an error occurs in the request process.

Reason: 

1. some of the fields may be empty/null

2. cpf already registered in the database

Example 1:
for a empty contact list
```
Validation Error: Property: contactList, Message: não deve estar vazio; 
```
Example 2:
```
Please confirm fields, datas can already exists
```

##### Not found 404
This response happens when an error occurs when returning the person.

Reason: Entity with this id is not registered in the database

Example:
```` 
Entity not found
````

### DELETE /id
Endpoint responsible for removing a person from the database

#### Parâmetros
id: person id

Exemple: /person/2

#### Server Response
##### No Content 204

It means the person was removed
