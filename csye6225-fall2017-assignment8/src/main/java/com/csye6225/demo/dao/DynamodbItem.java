package com.csye6225.demo.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;



@DynamoDBTable(tableName="csye6225")
public class DynamodbItem {

    private UUID value;
    private String key;

    @DynamoDBHashKey(attributeName="value")
    public UUID getValue() { return value;}
    public void setValue(UUID value) {this.value = value;}

    @DynamoDBAttribute(attributeName="key")
    public String getKey() {return key; }
    public void setKey(String key) { this.key = key; }


}
