#!/bin/sh
servers="localhost:8080 localhost:8081"

# JAX-RS
for server in $servers; do
  echo "Calling REST endpoint on $server"
  curl -s "${server}"/rest/fast
  curl -s "${server}"/rest/slow
done

# Agroal
for server in $servers; do
  echo "Calling Agroal operations on $server"
  curl -s "${server}"/datasource
done
