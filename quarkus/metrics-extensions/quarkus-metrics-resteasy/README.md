Try these endpoint methods
```
curl localhost:8080/hello/joe
curl localhost:8080/error
curl localhost:8080/a/b/c/list
curl localhost:8080/a/b/c/array
curl localhost:8080/a/b/c/varargs
curl localhost:8080/async
curl localhost:8080/async/exception
```                           

and then look at metrics using
```
curl localhost:8080/metrics/base | grep REST
```