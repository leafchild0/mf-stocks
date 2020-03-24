# Auth buddy

Small POC project to demonstrate power of microservices. 
Basically, has 4 microservices to manage users, data and gateway. 
There is also a client app that performs requests to services. This project is using Consul for service discovery.

#### Prerequisites
To run this project you will need 
    
    - Java 8 (minimum)
    - Node.js and npm to build client-side
    - Gradle to build the project
    - Consul (you can use docker file within the project or run standalone version)

#### Getting Started
TO get started clone this repo and follow instructions

#### Installing
Installing will require just 2 steps:

    - Build client-side code in auth-client module by running 'npm run build' 
    - Build everything else by running 'gradl build' in the root of the project

#### Running
Running will require the following steps:

    - Run consul and make sure it's running by visiting http://localhost:8500 
    - Run each of the services by either running main class in each service using your favorite IDE
    - OR running each service via 'java -jar <service-artifact>.jar'
    
After that, on [http://localhost:8080], client app will be waiting. There is a predefined user for testing: 
```json 
    {
        "username": "user2" 
        "password": 1 
    }
```

#### Built With

  * **Spring** - The web framework used
  * **Gradle** - Dependency Management
  * **Vue & Vuetify** - Used for client side
  * **Consul** - Service discovery
  * **Zuul** - micro proxy
  * **Ribbon** - client side load balancing

#### Contributing
Just create a Pull request...

#### Authors
Roman Rybak,
Victor Malyshev

---
#Additional info

### Architecture
Info about architecture of the project and example of typical login process

### Auth client
Simple Vue app backed by Spring boot. Can be easily replaced with node or any other platform. We used Spring to
comply with other services in the repo. 
This service has also basic requests authorization. Service should be visible outside and talk to gateway service.

### API Gateway
Routing gateway for all APIs. Built using Netflix Zuul. All the mapping can be found in the `application.yml
`. Contains 2 routes to auth-service and data-service apps. Has additional security checks to verify tokens.   

### JWT Auth server
This is simple JWT Auth Server. It should not be visible within outside.

#### Example Of Rest API
Register:

&nbsp;&nbsp; **URL** : `/api/auth/register`

&nbsp;&nbsp; **Method** : `POST`

&nbsp;&nbsp; **Body** :
```json
    {
        "username": "user1",
        "email": "user1@gmail.com",
        "password": "1"
    }
```

&nbsp;&nbsp; **Response Body (JWT_Token)** :
```text
    User registered successfully
```

Login:

&nbsp;&nbsp; **URL** : `/api/auth/login`

&nbsp;&nbsp; **Method** : `POST`

&nbsp;&nbsp; **Body** :
```json
    {
        "username": "user1",
        "password": "1"
    }
```

&nbsp;&nbsp; **Response Body (JWT_Token)** :
```json
    {
        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTc4MzMzOTAwLCJpYXQiOjE1NzgyMzM5MDB9.N01PscrwkVXmIi9L5WDi5jR_OpHy3Xy893tES3nZRQY",
        "tokenType": "Bearer"
    }
```

### Data service
An extremely simple microservice that manages some data. For the simplicity of the demo, it has only 1 API endpoint and
produce list of 'events'. Doesn't have any auth and should be hidden behind firewall. 
Return data requested by auth-client by using gateway service and discovery. 
 
#### Example Of Rest API
Get User Info:

&nbsp;&nbsp; **URL** : `/data/events`

&nbsp;&nbsp; **Header** : `Authorization: Bearer [JWT_Tocken]`

&nbsp;&nbsp; **Method** : `GET`

&nbsp;&nbsp; **Response Body (Data array)** :
```json
    {
        "events": "[...]"
    }
