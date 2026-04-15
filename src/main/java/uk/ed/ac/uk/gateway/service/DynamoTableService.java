package uk.ed.ac.uk.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import uk.ed.ac.uk.gateway.configuration.DynamoDBConfiguration;
import uk.ed.ac.uk.gateway.entity.DynamoImageQuery;
import uk.ed.ac.uk.gateway.entity.DynamoTextQuery;

@RequiredArgsConstructor
@Service
public class DynamoTableService {
    private final DynamoDBConfiguration dynamoDBConfiguration;
    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public void createImageTable() {
        // use enhanced client
        DynamoDbTable<DynamoImageQuery> imageTable = dynamoDbEnhancedClient.table(
                dynamoDBConfiguration.getDynamoDbImageTable(),
                TableSchema.fromBean(DynamoImageQuery.class)
        );
        imageTable.createTable();
    }

    public void createTextTable() {
        //use enhanced client
        DynamoDbTable<DynamoTextQuery> textTable = dynamoDbEnhancedClient.table(
                dynamoDBConfiguration.getDynamoDbTextTable(),
                TableSchema.fromBean(DynamoTextQuery.class)
        );
        textTable.createTable();

    }
}
