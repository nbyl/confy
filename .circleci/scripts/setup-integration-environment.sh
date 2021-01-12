#!/usr/bin/env bash

set -e
set -x

BINARY_DIRECTORY=".bin"

HELM_VERSION="3.4.2"
KIND_VERSION="0.9.0"
SKFAFFOLD_VERSION="1.2.0"

function downloadDependencies() {
  mkdir -p ${BINARY_DIRECTORY}

  curl -Lo ${BINARY_DIRECTORY}/kind https://kind.sigs.k8s.io/dl/v${KIND_VERSION}/kind-linux-amd64
  chmod +x ${BINARY_DIRECTORY}/kind
  curl -Lo /tmp/helm.tar.gz https://get.helm.sh/helm-v${HELM_VERSION}-linux-amd64.tar.gz
  tar -C /tmp -xzf /tmp/helm.tar.gz
  cp /tmp/linux-amd64/helm ${BINARY_DIRECTORY}

  curl -Lo ${BINARY_DIRECTORY}/skaffold https://github.com/GoogleContainerTools/skaffold/releases/download/v${SKFAFFOLD_VERSION}/skaffold-linux-amd64
  chmod +x ${BINARY_DIRECTORY}/skaffold

  curl -Lo ${BINARY_DIRECTORY}/kubectl https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
  chmod +x ${BINARY_DIRECTORY}/kubectl
}

function prepareCluster() {
  kind create cluster
  kubectl config use-context kind-kind
  kubectl cluster-info
}

function deployApplication() {
  helm repo add stable  https://charts.helm.sh/stable
  skaffold run
  kubectl rollout status deployment/confy
}

downloadDependencies

export PATH=${BINARY_DIRECTORY}:${PATH}

prepareCluster

deployApplication
