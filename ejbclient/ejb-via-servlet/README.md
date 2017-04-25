### Invoking an EJB remotely through a servlet

1. add user to EAP: `bin/add-user.sh -a -g users -u admin -p admin123+`
2. build and deploy the webapp
3. access the app using curl:
```
curl http://localhost:8080/ejb-via-servlet/ --basic -u admin:admin123+
```

