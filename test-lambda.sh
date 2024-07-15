#!/bin/bash

STACK_NAME=IacStack
aws --no-cli-pager cloudformation describe-stacks \
  --stack-name $STACK_NAME --query 'Stacks[0].Outputs' --output yaml

API_URL="$(aws --no-cli-pager cloudformation describe-stacks \
  --stack-name $STACK_NAME --query 'Stacks[0].Outputs[?OutputKey==`DemoTestApiUrl`].OutputValue' \
  --output text)"
RESPONSE="$(curl --header "Content-Type: application/json" --request GET --silent $API_URL)"
echo $RESPONSE
