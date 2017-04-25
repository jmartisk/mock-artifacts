1. unzip EAP distributions
2. add user to each EAP node with this command:  `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
3. build `server` project, deploy it to all nodes
4. run `client` project using `mvn exec:exec`

