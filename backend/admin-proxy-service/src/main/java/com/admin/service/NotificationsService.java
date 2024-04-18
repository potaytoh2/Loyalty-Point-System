package com.admin.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.admin.entity.Transaction;

@Service
public class NotificationsService {

    private final AmazonSQSAsync amazonSQSAsync;
    private String notificationQueueUrl, domain;

    @Autowired
    public NotificationsService(AmazonSQSAsync amazonSQSAsync, @Value("${sqs.queueName}") String queueName,
            @Value("${basedomain}") String domain) throws ExecutionException {
        this.amazonSQSAsync = amazonSQSAsync;
        this.domain = domain;
        try {
            notificationQueueUrl = amazonSQSAsync.createQueueAsync(queueName).get().getQueueUrl();
        } catch (InterruptedException e) {
        }
    }

    public void sendNotification(Transaction transaction) {
        String tID = transaction.getTransactionId();
        String messageBody = String.format(
                "{New transaction pending approval: \n\"transactionId\": \"%s\", \n\"transactionType\": \"%s\", \n\"details\": \"%s\"\nApprove it here: %s}",
                tID, transaction.getActionType(), transaction.getDetails(), domain + "/accept/" + tID);

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(notificationQueueUrl)
                .withMessageBody(messageBody);

        amazonSQSAsync.sendMessage(sendMessageRequest);
    }
}
