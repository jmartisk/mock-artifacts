## Supergraph file

The supergraph.graphql file is composed via rover. Download rover by running

```
curl -sSL https://rover.apollo.dev/nix/latest | sh
```

Then compose the supergraph.graphql file by running

```
rover supergraph compose --config supergraph-config.yaml > supergraph/supergraph.graphql
```

Both the graphql services (acmeshop on localhost:8080 and acmeshop-addons on localhost:8081 need to be running)

## Running the router

If you don't have the `router` binary installed, run
```
curl -sSL https://router.apollo.dev/download/nix/latest | sh
```

Then start the router with:

```
router --dev --supergraph supergraph/supergraph.graphql
```

Then, the federated service should be running on localhost:4000
