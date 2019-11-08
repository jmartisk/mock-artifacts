Users
-----
- `joe` with password `blabla123` can only access the metrics endpoint
- `kim` with password `blabla456` can only access the metrics controller
- `manny` with password `blabla789` can access both

Accessing the app
-----------------
Will work:
- `curl -v --user joe:blabla123 localhost:8080/metrics`
- `curl -v --user kim:blabla456 localhost:8080/metrics-controller/counter`

Won't work:
- `curl -v --user kim:blabla456 localhost:8080/metrics`
- `curl -v --user joe:blabla123 localhost:8080/metrics-controller/counter`

Running the tests
-----------------
TODO: implement and document tests for this