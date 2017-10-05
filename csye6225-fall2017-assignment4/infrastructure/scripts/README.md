Team member information such as Name and Email address.

Amitha_Murali, 001643826, murali.a@husky.neu.edu 
Jyoti Sharma, 001643410, sharma.j@husky.neu.edu 
Surabhi Patil, 001251860, patil.sur@husky.neu.edu

AWS CLI:

Prerequisites:

1. setup aws cli in your linux machine using the following link:
http://docs.aws.amazon.com/cli/latest/userguide/awscli-install-linux.html

2. Run 'aws configure' command and enter access key id, secret access key.

3. Validate if your key file is placed in the same folder as scripts before proceeding.

Run commands:
Open terminal and run the following commands 

1. To launch : sh launch-ec2-instance.sh
2. To terminate : sh terminate-ec2-instance.sh 'instance-id' where instance-id needs to be passed through the command line.

Validate output: 
check if the security group, ec2 instance and resource records were successfully created with the mentioned parameters and later deleted through the delete script.





