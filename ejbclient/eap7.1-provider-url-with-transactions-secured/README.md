### EJB client with transactions using PROVIDER_URL approach

1. unzip EAP distro
2. add user to EAP with this command:  `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
3. build the `server` project, deploy it
4. run `client` project using `mvn exec:exec`

