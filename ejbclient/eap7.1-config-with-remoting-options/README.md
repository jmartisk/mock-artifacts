1. unzip EAP distro
2. add user to EAP with this command:  `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
3. build `server` project, deploy it
4. run `client` project using `mvn package exec:exec`
5. To trigger a timeout, you can temporarily freeze the EAP process by pressing Ctrl-Z in its terminal window while an EJB invocation is running... (type 'fg' to unpause it then)

