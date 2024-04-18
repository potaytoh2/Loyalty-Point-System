package com.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean
	@Order(1)
	@ConditionalOnProperty("AWS_CONTAINER_CREDENTIALS_RELATIVE_URI")
	public AmazonDynamoDB dbdefault() {
		return AmazonDynamoDBClientBuilder.defaultClient();
	}

	@Bean
	@Order(1)
	@ConditionalOnProperty("AWS_CONTAINER_CREDENTIALS_RELATIVE_URI")
	public AmazonSQSAsync sqsdefault() {
		return AmazonSQSAsyncClientBuilder.defaultClient();
	}

	@Bean
	@Order(2)
	@ConditionalOnMissingBean
	public AmazonDynamoDB db(@Value("${dynamo.host}") String host, @Value("${REGION}") String region) {
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new EndpointConfiguration(host, region))
				.build();
	}

	@Bean
	@Order(2)
	@ConditionalOnMissingBean
	public AmazonSQSAsync sqs(@Value("${sqs.host}") String host, @Value("${REGION}") String region) {
		return AmazonSQSAsyncClientBuilder.standard()
				.withEndpointConfiguration(new EndpointConfiguration(host, region))
				.build();
	}
}
