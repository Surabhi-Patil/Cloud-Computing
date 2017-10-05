## 1) EC2-VPC	
echo "Creating a security group for EC2 instance"
# aws configure

# Exporting ec2-vpc id
export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)
echo $VPC_ID

# Creating a Security Group using VPC ID
export SG_NAME=$(aws ec2 create-security-group --group-name csye6225-fall2017-webapp --description "Security group for csye6225-fall2017-webapp" --vpc-id $VPC_ID)
echo $SG_NAME

# To view the initial information for the created security grp
aws ec2 describe-security-groups --group-ids $SG_NAME

# Adding Rules to Your Security Group

echo "Adding Rules to the security group"

# The following command adds a rule for SSH to the security group
aws ec2 authorize-security-group-ingress --group-id $SG_NAME --protocol tcp --port 22 --cidr 0.0.0.0/0

# The following command adds a rule for HTTP to the security group
aws ec2 authorize-security-group-ingress --group-id $SG_NAME --protocol tcp --port 80 --cidr 0.0.0.0/0

# The following command adds a rule for HTTPS to the security group
aws ec2 authorize-security-group-ingress --group-id $SG_NAME --protocol tcp --port 443 --cidr 0.0.0.0/0

################################ EC2 Instance commands#########################################

#create an ec2 instance with the following parameters - AMI = Ubuntu Server 16.04 LTS,
#Instance type = t2.micro, protect against accidental termination,root vol siz = 16, 
#root vol type = General Purpose SSD (GP2), Security Group = csye6225-fall2017-webapp

echo "Creating the EC2 instance"

INSTANCE_ID = $(aws ec2 run-instances --image-id ami-cd0f5cb6 --security-group-ids $SG_NAME --count 1 --instance-type t2.micro --key-name aws-key-pair --region us-east-1 --query 'Instances[0].InstanceId' --block-device-mappings "[{
   \"DeviceName\": \"/dev/sda1\",
    \"Ebs\": {
      \"DeleteOnTermination\": false,
      \"SnapshotId\": \"snap-0cfc17b071e696816\",
      \"VolumeSize\": 16,
      \"VolumeType\": \"gp2\"
    }
  }]")

echo $INSTANCE_ID

#Enable the protection against termination.
aws ec2 modify-instance-attribute --instance-id $INSTANCE_ID --disable-api-termination

echo "Retrieving public IP of the instance"

PUB_IP=$(aws ec2 describe-instances --instance-ids $INSTANCE_ID --query "Reservations[0].Instances[0].PublicIpAddress" --output text)
echo $PUB_IP

echo "Creating a Record Set"

aws route53 change-resource-record-sets --hosted-zone-id Z1V8ZHS4H3Z5H7 --change-batch "{\"Comment\": \"DNS name for my instance.\", \"Changes\":[{\"Action\": \"CREATE\", \"ResourceRecordSet\": { \"Name\": \"ec2.csye6225-fall2017-patilsur.me\", \"Type\": \"A\", \"TTL\": 60, \"ResourceRecords\": [{\"Value\": \""$PUB_IP"\"}]}}]}"
