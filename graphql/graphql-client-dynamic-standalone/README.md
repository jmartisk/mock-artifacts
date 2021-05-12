## Standalone (pure Java SE) GraphQL dynamic client application

- First, in another terminal, run the Quarkus project from `../graphql-quarkus` or `../graphql-wildfly`
- Then,

Query: `mvn package exec:java`
Mutation: `mvn package exec:java -DmainClass=org.example.graphql.client.ClientMainQuery`
