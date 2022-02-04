## Server side
Run the server side using the `../graphql-quarkus` project.

## Run the client side
`mvn clean quarkus:dev -Dquarkus.http.port=4444`

### Typesafe
- `curl localhost:4444/typesafe/query`
- `curl localhost:4444/typesafe/subscription/start`
- `curl localhost:4444/typesafe/subscription/stop`
- `curl localhost:4444/typesafe/uni`
                                                          
### Dynamic
- `curl localhost:4444/dynamic/query`
- `curl localhost:4444/dynamic/subscription/start`
- `curl localhost:4444/dynamic/subscription/stop`