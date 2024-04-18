package com.admin.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.entity.Log;

@Service
public class LogService {

    private final DynamoDBTableMapper<Log, String, ?> mapper;

    @Autowired
    public LogService(AmazonDynamoDB amazonDynamoDB) {
        mapper = new DynamoDBMapper(amazonDynamoDB).newTableMapper(Log.class);
        mapper.createTableIfNotExists(new ProvisionedThroughput(5L, 5L));
    }

    public void logAction(String action, String adminId, String details, String userAgent, String approximateLocation) {
        logAction(new Log.Builder()
                .withAction(action)
                .withAdminId(adminId)
                .withDetails(details)
                .withUserAgent(userAgent)
                .withApproximateLocation(approximateLocation)
                .build());
    }

    public void logAction(Log log) {
        mapper.save(log);
    }

    public List<Log> getLogs() {
        return mapper.scanPage(new DynamoDBScanExpression()).getResults();
    }
}
