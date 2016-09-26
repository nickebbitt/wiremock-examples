#!/usr/bin/env bash

set -o errexit
set -o nounset

declare -r gv_server_port=9000

echo
echo Starting language-service on port ${gv_server_port}...
echo
java -jar target/language-service-0.0.1-SNAPSHOT.jar --server.port=${gv_server_port}
echo


