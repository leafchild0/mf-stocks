# History Service
Project base on Kotlin and VueJs

## How to build 

Project consists of frontend project anb server project. 
Build of the server side initiate build of the frontend side.
```shell script
 > cd server
 > ./gradlew build
```

## How to run

The file db.json should be in the server folder.
```shell script
 > cd server 
 > java -Dserver.port=8090 -jar build/libs/server-0.0.1-SNAPSHOT.jar
```
