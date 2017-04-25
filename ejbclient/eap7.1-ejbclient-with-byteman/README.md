### EJB client with a Byteman script on the client side

1. unzip EAP distro
2. add user to EAP with this command: `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
3. build the `server` project, deploy it
4. run client side using `mvn exec:exec -Dbyteman.home=$BYTEMAN_HOME` (you need to have Byteman unzipped somewhere)
5. the client-side byteman script is in `client/script.btm`
