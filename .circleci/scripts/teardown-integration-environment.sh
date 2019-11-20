#!/usr/bin/env bash

set -e
set -x

BINARY_DIRECTORY=".bin"

function deleteCluster() {
  kind delete cluster
}

export PATH=${BINARY_DIRECTORY}:${PATH}

deleteCluster
