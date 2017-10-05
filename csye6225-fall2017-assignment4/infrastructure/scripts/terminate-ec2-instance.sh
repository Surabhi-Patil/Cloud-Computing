echo "Deleting a Record Set"

aws route53 change-resource-record-sets --hosted-zone-id Z1V8ZHS4H3Z5H7 --change-batch  "{\"Comment\": \"DNS name for my instance.\", \"Changes\":[{\"Action\": \"DELETE\", \"ResourceRecordSet\": { \"Name\": \"ec2.csye6225-fall2017-patilsur.me\", \"Type\": \"A\", \"TTL\": 60, \"ResourceRecords\": [{\"Value\": \""$PUB_IP"\"}]}}]}"

echo "Terminating ec2 instance"

#Disable the protection against termination.
aws ec2 modify-instance-attribute --instance-id $1 --no-disable-api-termination

#Terminate the ec2 instance
aws ec2 terminate-instances --instance-ids $1

sleep 15s

echo "Deleting Security Group"
# Deleting Your Security Group
aws ec2 delete-security-group --group-name csye6225-fall2017-webapp
