## Load balancing and failover obtained by specifying multiple PROVIDER_URLs                                                                                                                                                                    


1. unzip 2 EAP distros
2. add user to both EAPs with this command:  bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!
3. run both servers, bind one to 127.0.0.1 and the other to 127.0.0.2, give them distinct jboss.node.name properties
4. build server side project, deploy to both servers
5. run client side using mvn exec:exec
6. The client will send an invocation each 1 second. 
Try shutting down and re-starting servers while the client is running and see how the client behaves. 
