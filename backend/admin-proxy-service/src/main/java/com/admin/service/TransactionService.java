package com.admin.service;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.entity.Transaction;
import com.admin.exception.TransactionNotFoundException;
import com.admin.utils.JWTUtils;

@Service
public class TransactionService {

    private final DynamoDBTableMapper<Transaction, String, ?> mapper;
    private final NotificationsService notificationsService;
    private final ProxyService proxy;

    @Autowired
    public TransactionService(AmazonDynamoDB amazonDynamoDB, NotificationsService notificationService,
            ProxyService proxy) {
        mapper = new DynamoDBMapper(amazonDynamoDB).newTableMapper(Transaction.class);
        mapper.createTableIfNotExists(new ProvisionedThroughput(5L, 5L));
        this.notificationsService = notificationService;
        this.proxy = proxy;
    }

    public void createTransaction(String actionType, String initatedBy, String status, String details, String service) {
        createTransaction(new Transaction.Builder()
                .withActionType(actionType)
                .withInitiatedBy(initatedBy)
                .withStatus(status)
                .withDetails(details)
                .build());
    }

    public void createTransaction(Transaction transaction, String service, String id) {
        if (service.equals("user") || service.equals("points")) {
            transaction.setService(service);
            transaction.setTarget(service.equals("user") ? "/info/" + id : "/points/" + id);
            createTransaction(transaction);
        }
    }

    public void createTransaction(Transaction transaction) {
        mapper.save(transaction);
        notificationsService.sendNotification(transaction);
    }

    public void updateTransactionStatus(String transactionId, boolean approved) {
        Transaction transaction = mapper.load(transactionId);
        if (transaction != null) {
            setStatus(transaction, approved);
            mapper.save(transaction);
            notificationsService.sendNotification(transaction); // notify relevant parties about updated transaction
        } else {
            throw new TransactionNotFoundException(transactionId);
        }
    }

    private void setStatus(Transaction transaction, boolean approved) {
        transaction.setStatus(approved ? "approved" : "rejected");
        if (approved) {
            proxy.approveRequest(transaction);
        }
    }

    public void createUserTransaction(String body, Map<String, String> headers, String id) {
        createTransaction(new Transaction.Builder()
                .withActionType("POST")
                .withDetails(body)
                .withInitiatedBy(JWTUtils.get_sub(headers.get("Authorization".toLowerCase())))
                .withStatus("pending")
                .build(), "user", id);
    }

    public void createPointsTransaction(String body, Map<String, String> headers, String id) {
        createTransaction(new Transaction.Builder()
                .withActionType("PUT")
                .withDetails(body)
                .withInitiatedBy(JWTUtils.get_sub(headers.get("Authorization".toLowerCase())))
                .withStatus("pending")
                .build(), "points", id);
    }
}
