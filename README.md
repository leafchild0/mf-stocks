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
        "username": "user2", 
        "password": "1"
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
# Additional info

### Architecture
This project main goal is to demonstrate a simple way of using modern microservices architecture for any application. Including auth and services orchestration. Both problems are well defined and solved in elegant way which includes all best practices for microservices development like scaling, fault-tolerance, monitoring, easy deployments and many more. 

Service discovery is one of the most important thing in the microservices. Nowadays, it's really hard to maintain more than 5 microservices using hard-coded values and simple DNS. It's ineffective and pain to deploy. And then support, monitor and troubleshoot. Service orchestration and discovery have been created in order to minimize this pain. 

Service orchestration is leveraging HashiCorp Consul, but there are other alternatives (Eureka, Zookeeper, etc). Simply putting, each service register itself in the registry and all other communication is performed using services names within discovery network. Consul is responsible for health checking and management of all registered services. For this purpose, it's using health-checks. There are few predefined ones, but developer can always add a custom one (you can refer to an example in the project). Discovery allows services talk to each other without knowing where exactly those services are. This is ideal for increasing demand of containers and k8s in the world.

---

To keep this project simple, it contains minimum amount of services, but with this structure any new service can be added in a minimum amount of time. Or any existing service can be scaled and load balanced between those services by using few annotations and editing few lines in config. But, still those services are:

- Simple Client to provide some UI to user
- API Gateway to manage and route traffic for all services
- Auth service to manage users and their roles
- Data service to manage data that is not related to auth

Image below demonstrates high level architecture

![Architecture](https://cacoo.com/diagrams/euRtCqRI8DQBg0sP-EDA19.png)

Short description about each service and it's role in the system

**Simple client**

Spring boot app which serves a Vue static files. This service has 3 benefits: it's authorizes and logs requests coming from user's browser, it serves Vue static files and it REALLY simplifies frontend development. It's rather simple, but for the purposes of the demo, it contains 3 Views: Login, Sign up and Home pages. First 2 pages are obvious, 3rd page displays some data from data service. Just to demonstrate login was successful. This service should be exposed to all users and outside world.

**API Gateway**

One of the most important services. It holds list of routes for all the services and manages the traffic within the system. This service knows where to redirect requests for auth or data. This is done using Netflix Zuul - micro proxy. Additionally, for security purposes, it also additionally checks JWT token for every request. In this manner, API will be responsible to check user permissions and roles before re-routing certain request. In real world scenario, gateway should be exposed to a real world and load balanced using nginx or similar.

**Auth service**

This service manages users and authenticate them. It has an H2 built in with JPA and stuff. There is also roles support. Service is using simple mechanism with JWT tokens, to sticky sessions, no session support at all. It basically can just verify the token in this case. This is ideal place to add any other auth if needed. For example LDAP or SAML integration. This services should be hidden by a firewall.

**Data service**

Simplest service among others. For the purpose of demo, it contains only 1 REST endpoint which provides some data to be shown for user. It doesn't have security built in and only gateway knows about it. Or, to be precise, only discovery services knows about it. Just like auth service, it should be hidden behind firewall and only gateway should have access to it.

Overall, this is simplest architecture I can ever thing of in order to demonstrate authentication. And it has it's benefits:

- No hardcoded or any kind of data about services (like IP)
- Each of the services can be scaled separately
- Docker, k8s friendly architecture
- Rather easy deployments
- Best tools for monitoring, fault-tolerance, resiliency
- Load-balancing out of the box with Netflix Ribbon

---

It's important to understand how things are working on a simple example. Basic login process can be described as follows

![Login process](https://github.com/leafchild0/auth-buddy/blob/master/Login%20process.png)

What's happening:

1. User open client app url and see login page, type login and password. That request goes directly to API gateway, no hard-code needed, just using service name in the URL
2. API gateway check all the routes and re-route request to auth service
3. Auth service use passed username to find user and authenticate it using passed password
4. If everything is OK, auth server will return a token to the client app. In case of failure - will return an error
5. Client app then use token and pass it within each request and send a request to fetch some data
6. This request goes to API gateway and decide that request should be re-routed to data service, but first token should be verified. If everything is OK, request is will be passed to data service
7. Data service returns data back to client app
8. Client app displays the data to user

---
# Services

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
