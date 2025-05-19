#!/bin/bash

az group create --name rg-challenge --location brazilsouth

az vm create \
  --resource-group rg-challenge \
  --name vm-challenge \
  --image almalinux:almalinux-x86_64:8-gen1:latest \
  --admin-username admlnx \
  --admin-password Fiap@2tdsvms \
  --generate-ssh-keys

az vm open-port --port 22 --resource-group rg-challenge --name vm-challenge

az vm open-port --port 8080 --resource-group rg-challenge --name vm-challenge --priority 1300