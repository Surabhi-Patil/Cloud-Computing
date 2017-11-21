package com.csye6225.demo.auth;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.regions.Regions;

@Configuration
public class AWSConfiguration {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region}")
    private String region;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        //return new BasicAWSCredentials(System.getProperty("accessKey"), System.getProperty("secretKey"));
        return new BasicAWSCredentials(accessKey,secretKey);
    }

    @Bean
    public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
        AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
        amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
        return amazonS3Client;
    }

    @Bean
    public AmazonSNSClient amazonSNSClient(AWSCredentials awsCredentials) {
        AmazonSNSClient snsClient = new AmazonSNSClient(awsCredentials);
        snsClient.setRegion(Region.getRegion(Regions.fromName(region)));
        return snsClient;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDBClient(AWSCredentials awsCredentials){

        // This client will default to US West (Oregon)
        AmazonDynamoDB dynamoDBclient = new AmazonDynamoDBClient(awsCredentials);
        //dynamoDBclient.withRegion(Regions.fromName(region)).build();
        return dynamoDBclient;
    }
}