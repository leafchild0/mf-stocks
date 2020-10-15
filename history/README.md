# History Service
Project base on Kotlin and VueJs

## How to build 

Project consists of frontend project anb server project. 
Build of the server side initiate build of the frontend side.
```shell script
 > cd server
 > ./gradlew build
```

## How to run Dev project from CL

The file db.json should be in the server folder.
```shell script
 > cd server 
 > java -Dserver.port=9000 -Dspring.profiles.active=dev -Dhistory.db.location=db.json -jar build/libs/server-0.0.1-SNAPSHOT.jar
```

## Add new history record

```shell script
curl -X POST \
  http://localhost:9000/history \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
      "stockName":"Tesla",
      "date":"2020-04-23T18:25:43.511Z",
      "username":"userName_4",
      "amount":21,
      "type":"SELL"
}'
``` 