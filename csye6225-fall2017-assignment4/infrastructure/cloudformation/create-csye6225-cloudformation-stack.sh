# Create CloudFormation Stack
echo "Creating CloudFormation Stack"
aws cloudformation create-stack --stack-name $1 --template-body file://./createStack.json

aws cloudformation validate-template --template-body file://./createStack.json
