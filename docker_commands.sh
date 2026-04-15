#!/bin/bash

# compose docker image
docker buildx build --platform=linux/amd64,linux/arm64 -t acp-gateway --load .

# save to tar file
docker image save acp-gateway -o acp_gateway.tar

# run command
docker run -d --publish 8080:8080 --name acp-gateway \
  --network aigc_network \
  acp-gateway

