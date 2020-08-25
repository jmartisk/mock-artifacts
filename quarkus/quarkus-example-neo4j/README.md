## Start 
```
podman run -p 7474:7474 -p 7687:7687 -e 'NEO4J_AUTH=neo4j/secret' neo4j
mvn quarkus:dev
```                      

## Play with data
```
curl localhost:8080/create
curl localhost:8080/get
```

