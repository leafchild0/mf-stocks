
## How to run project

1. Build all project
```shell
./gradlew clean build
```

2. Run docker-compose
```shell script
docker-compose up
```

###Docker-compose run:

3rd party services:
1. Stock MongoDB
2. Consul (done)

MF Stocks Services: 
1. Auth-service (done)
2. History-service-ui (done)
3. Stocks-service-ui (done)
4. User-account-service-ui (done)
5. Cart (InProgress)
6. App-Shell (InProgress)
7. Gateway-service (done)



All available routes for Gateway: 
```javascript
  http://localhost/actuator/routes
```

URL list for services:

```javascript
auth-service: 
    http://localhost/auth/
history-service-ui:
    http://localhost/history
stocks-service-ui:
    http://localhost/stocks
user-account-service-ui:
    http://localhost/user-account
```
