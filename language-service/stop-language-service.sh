#!/usr/bin/env bash

set -o errexit
set -o nounset

declare -r gv_server_port=9000

echo
echo Stopping language-service on port ${gv_server_port}...
echo
curl -X POST localhost:${gv_server_port}/shutdown
echo


