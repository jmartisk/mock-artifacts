### Example of connecting a remote JMX client

1. Add management user to EAP:
```
bin/add-user.sh -m -u admin -p pass.1234
```
2. Run the example using `mvn package exec:exec`

You can also specify `-Dmainclass` - there are two possible main classes:
- `example.remotejmx.Main` - using Elytron APIs for authentication (this is the default)
- `example.remotejmx.MainWithoutElytron` - using standard JDK APIs for authentication 

