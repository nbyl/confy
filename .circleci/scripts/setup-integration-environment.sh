#!/usr/bin/env bash

set -e
set -x

BINARY_DIRECTORY=".bin"

function downloadDependencies() {
  mkdir -p ${BINARY_DIRECTORY}

  curl -Lo ${BINARY_DIRECTORY}/kind https://github.com/kubernetes-sigs/kind/releases/download/v0.5.1/kind-$(uname)-amd64
  chmod +x ${BINARY_DIRECTORY}/kind

  curl -Lo /tmp/helm.tar.gz https://get.helm.sh/helm-v3.0.0-linux-amd64.tar.gz
  tar -C /tmp -xzf /tmp/helm.tar.gz
  cp /tmp/linux-amd64/helm ${BINARY_DIRECTORY}

  curl -Lo ${BINARY_DIRECTORY}/skaffold https://storage.googleapis.com/skaffold/releases/latest/skaffold-linux-amd64
  chmod +x ${BINARY_DIRECTORY}/skaffold

  curl -Lo ${BINARY_DIRECTORY}/kubectl https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
  chmod +x ${BINARY_DIRECTORY}/kubectl
}

function prepareCluster() {
  kind create cluster
  export KUBECONFIG="$(kind get kubeconfig-path --name="kind")"
  kubectl cluster-info
}

function deployApplication() {
  helm repo add stable https://kubernetes-charts.storage.googleapis.com
  skaffold run
  kubectl rollout status deployment/confy
}

downloadDependencies

export PATH=${BINARY_DIRECTORY}:${PATH}

prepareCluster

deployApplication
