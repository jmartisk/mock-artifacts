This application calls a GraphQL endpoint in a potentially different JVM.
The app itself runs inside WildFly.

## How to run
- Run a graphql endpoint, for example using the project `../graphql-quarkus` (if using a separate JVM, run it on a different port!)
- Deploy this to a WildFly instance with the GraphQL feature pack installed 
- If the target endpoint is on a different port, pass `-Dport=xxxx` to the WildFly instance
- `curl localhost:8080/graphql-client-example/all`