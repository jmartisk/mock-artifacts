# Thanks to WS-Addressing, you should receive the response on port localhost:7778
echo "************* Sending SOAP message: *************"
cat msgs/SimpleWebService/throwFault
echo "************* Response: *************"
curl -d@msgs/SimpleWebService/sayHello http://localhost:8080/mock-jaxws/SimpleWebService
echo ""