#!/bin/bash

#Surabhi Patil, 001251860, patil.sur@husky.neu.edu
#Amitha Murali, 001643826, murali.a@husky.neu.edu
#Jyoti Sharma, 001643410, sharma.j@husky.neu.edu
# Create CloudFormation Stack
echo "Creating CloudFormation Stack"

echo "Please enter a stack name"
read stackName #mydemostack
echo "Please enter vpcId"
read vpcId #vpc-b197cdd7
echo "Please enter key name"
read keyName #ec2Key
echo "Please enter imageId"
read imageId #ami-cd0f5cb6
echo "Please enter snapshot Id"
read snapshotId #snap-0ed8c6d8a0e224c3c
echo "Please enter the instance type"
read InstType #t2.micro
echo "Please enter the volume size"
read volumeSize  #16
echo "Please enter the volume type"
read volumeType #gp2
echo "Please enter the hosted Zone Id"
read hostedZoneId #Z3T438XNMHHR1A
echo "Please enter the dns name"
read dnsName #ec2.csye6225-fall2017-muralia.me
echo "Please enter the RecordSet Type"
read recordSetType #A
echo "Please enter the RecordSeT Time to Live"
read recordSetTTL #60
echo "Please enter the rdsParamUsername"
read rdsParamUsername #csye6225master
echo "Please enter the rdsParamPassword"
read rdsParamPassword #csye6225password

aws cloudformation create-stack --stack-name $stackName --template-body file://./createStack.json --parameters ParameterKey=imageId,ParameterValue=$imageId ParameterKey=vpcId,ParameterValue=$vpcId ParameterKey=keyName,ParameterValue=$keyName ParameterKey=snapshotId,ParameterValue=$snapshotId ParameterKey=InstType,ParameterValue=$InstType ParameterKey=volumeSize,ParameterValue=$volumeSize ParameterKey=volumeType,ParameterValue=$volumeType ParameterKey=hostedZoneId,ParameterValue=$hostedZoneId ParameterKey=dnsName,ParameterValue=$dnsName ParameterKey=recordSetType,ParameterValue=$recordSetType ParameterKey=recordSetTTL,ParameterValue=$recordSetTTL

aws cloudformation validate-template --template-body file://./createStack.json

#aws cloudformation create-stack --stack-name mydemostack --template-body file:///home/cloud/GitSubmissions/csye6225-fall-2017-New/csye6225-fall2017/infrastructure/aws/cloudformation/createStack.json --capabilities "CAPABILITY_NAMED_IAM" --parameters ParameterKey=imgId,ParameterValue=ami-cd0f5cb6 ParameterKey=vpcId,ParameterValue=vpc-b197cdd7 ParameterKey=keyName,ParameterValue=ec2Key ParameterKey=snapshotId,ParameterValue=snap-0ed8c6d8a0e224c3c ParameterKey=InstType,ParameterValue=t2.micro ParameterKey=volumeSize,ParameterValue=16 ParameterKey=volumeType,ParameterValue=gp2 ParameterKey=hostedZoneId,ParameterValue=Z3T438XNMHHR1A ParameterKey=dnsName,ParameterValue=ec2.csye6225-fall2017-muralia.me ParameterKey=recordSetType,ParameterValue=A ParameterKey=recordSetTTL,ParameterValue=60 ParameterKey=rdsParamUsername,ParameterValue=csye6225master ParameterKey=rdsParamPassword,ParameterValue=csye6225password


#aws cloudformation create-stack --stack-name teststack --template-body file:///home/surabhi/GitCommits/csye6225-fall2017-1/infrastructure/aws/cloudformation/createStack.json --capabilities "CAPABILITY_NAMED_IAM" --parameters ParameterKey=imageId,ParameterValue=ami-cd0f5cb6 ParameterKey=vpcId,ParameterValue=vpc-56fd1a2e ParameterKey=keyName,ParameterValue=aws-key-pair ParameterKey=snapshotId,ParameterValue=snap-0cfc17b071e696816 ParameterKey=InstType,ParameterValue=t2.micro ParameterKey=volumeSize,ParameterValue=16 ParameterKey=volumeType,ParameterValue=gp2 ParameterKey=hostedZoneId,ParameterValue=Z1V8ZHS4H3Z5H7 ParameterKey=dnsName,ParameterValue=ec2.csye6225-fall2017-patilsur.me ParameterKey=recordSetType,ParameterValue=A ParameterKey=recordSetTTL,ParameterValue=60 ParameterKey=rdsParamUsername,ParameterValue=csye6225master ParameterKey=rdsParamPassword,ParameterValue=csye6225password
























