#!/bin/bash

#Surabhi Patil, 001251860, patil.sur@husky.neu.edu
#Amitha Murali, 001643826, murali.a@husky.neu.edu
#Jyoti Sharma, 001643410, sharma.j@husky.neu.edu
# Create CloudFormation Stack
echo "Creating CloudFormation Stack"

echo "Please enter a stack name"
read stackName
echo "Please enter vpcId"
read vpcId
echo "Please enter key name"
read keyName
echo "Please enter imageId"
read imageId
echo "Please enter snapshot Id"
read snapshotId
echo "Please enter the instance type"
read InstType
echo "Please enter the volume size"
read volumeSize
echo "Please enter the volume type"
read volumeType
echo "Please enter the hosted Zone Id"
read hostedZoneId
echo "Please enter the dns name"
read dnsName
echo "Please enter the RecordSet Type"
read recordSetType
echo "Please enter the RecordSeT Time to Live"
read recordSetTTL

aws cloudformation create-stack --stack-name $stackName --template-body file://./createStack.json --parameters ParameterKey=ImgId,ParameterValue=$imageId ParameterKey=VpcId,ParameterValue=$vpcId ParameterKey=KeyName,ParameterValue=$keyName ParameterKey=SnapshotId,ParameterValue=$snapshotId ParameterKey=InstType,ParameterValue=$InstType ParameterKey=volumeSize,ParameterValue=$volumeSize ParameterKey=volumeType,ParameterValue=$volumeType ParameterKey=hostedZoneId,ParameterValue=$hostedZoneId ParameterKey=dnsName,ParameterValue=$dnsName ParameterKey=recordSetType,ParameterValue=$recordSetType ParameterKey=recordSetTTL,ParameterValue=$recordSetTTL

aws cloudformation validate-template --template-body file://./createStack.json
