# The service will throw a FAULT.
# Thanks to WS-Addressing, you should receive the response (containing the fault) on port localhost:7777
echo "************* Sending SOAP message: *************"
cat msgs/SimpleWebService/throwFault
echo "************* Response: *************"
curl -d@msgs/SimpleWebService/throwFault http://localhost:8080/mock-jaxws/SimpleWebService
echo ""
