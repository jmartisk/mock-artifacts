# GraphQL client based on Quarkus that connects to Hasura and uses subscriptions


## Prepare Hasura 

### Run Hasura and database
```
podman-compose up
# hasura UI is now available at http://localhost:7777/console
# postgres database is now available at localhost:5432 
# now add a datasource in the UI: http://localhost:7777/console/data/manage/connect
# db url should be postgres://postgres:postgrespassword@postgres:5432/postgres
```

### Add some testing data
```
# (install postgresql client using sudo dnf install postgresql) 
psql -h localhost -p 5432 -U postgres -f import-data.sql
```

## Run the client

### Typesafe client

### Dynamic client

## Do changes to the database to receive them by the client

## Cleanup
```
psql -h localhost -p 5432 -U postgres -f clear-data.sql
podman-compose down
```
