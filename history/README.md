# history

### Install mock json server 

```shell script
npm install -g json-server
```

### Run mock json server 

```shell script
json-server --watch db.json
```


### Docker build 

```shell script
docker build --tag st-history:1.0 .
```

### Docker run  

```shell script
docker run --publish 8080:8080 --detach --name st_h st-history:1.0
```


### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
