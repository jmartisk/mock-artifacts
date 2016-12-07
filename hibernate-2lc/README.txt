How to configure the cache:

/subsystem=infinispan/cache-container=hibernate/local-cache=awesomeCache:add
/subsystem=infinispan/cache-container=hibernate/local-cache=awesomeCache/transaction=TRANSACTION:add(mode=NON_XA)