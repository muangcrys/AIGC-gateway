package uk.ed.ac.uk.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfiguration {

    @Value( "${DYNAMO_ENDPOINT:http://host.docker.internal:4566}")
    private String dynamoDbEndpoint;

    @Bean(name = "dynamoDbEndpoint")
    public String getDynamoDbEndpoint(){
        return dynamoDbEndpoint;
    }

    @Value("${DYNAMO_TABLE_TEXT:text_queries}")
    private String dynamoDbTextTable;

    @Bean(name = "dynamoDbTextTable")
    public String getDynamoDbTextTable() {
        return dynamoDbTextTable;
    }

    @Value("${DYNAMO_TABLE_IMAGE:image_queries}")
    private String dynamoDbImageTable;

    @Bean(name = "dynamoDbImageTable")
    public String getDynamoDbImageTable() {
        return dynamoDbImageTable;
    }

    @Bean(name = "AWSregion")
    public Region getAWSregion() {
        return Region.US_EAST_1;
    }

    @Value("${AWS_USER:test}")
    private String awsUser;

    @Value("${AWS_SECRET:test}")
    private String awsSecret;

    @Bean(name = "awsUser")
    public String getAwsUser() {
        return awsUser;
    }

    @Bean(name = "awsSecret")
    public String getAwsSecret() {
        return awsSecret;
    }

    @Bean(name = "dynamoDBClient")
    public DynamoDbClient getDynamoDBClient() {
        return DynamoDbClient.builder()
                .endpointOverride(java.net.URI.create(dynamoDbEndpoint))
                .region(getAWSregion())
                .credentialsProvider(StaticCredentialsProvider.create(software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create(awsUser, awsSecret)))
                .build();
    }

    @Bean(name = "dynamoDBEnhancedClient")
    public DynamoDbEnhancedClient getDynamoDBEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(getDynamoDBClient())
                .build();
    }

}
