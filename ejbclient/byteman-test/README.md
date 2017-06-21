# Test for EJB client using Byteman on the client side - EAP 7.1+

1. Build the `server` project and deploy it to a running EAP instance
2. Run the test from the `client` directory using mvn test
3. The byteman script is defined using `@BMRules` annotation in the `ClientTest` class 