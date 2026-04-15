package uk.ed.ac.uk.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import uk.ed.ac.uk.gateway.configuration.DynamoDBConfiguration;
import uk.ed.ac.uk.gateway.dto.QueryOverview;
import uk.ed.ac.uk.gateway.dto.SubmissionTicket;
import uk.ed.ac.uk.gateway.entity.DynamoImageQuery;
import uk.ed.ac.uk.gateway.entity.DynamoQuery;
import uk.ed.ac.uk.gateway.entity.DynamoTextQuery;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QueryService {
    private final DynamoDBConfiguration dynamoDBConfiguration;
//    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private List<DynamoQuery> getQueriesForUser(String username, String tableName) {
        DynamoDbTable<DynamoQuery> table = dynamoDbEnhancedClient.table(
                tableName,
                TableSchema.fromBean(DynamoQuery.class)
        );

        QueryConditional condition = QueryConditional.keyEqualTo(k -> k.partitionValue(username));

        return table.query(r -> r
                .queryConditional(condition))
                .items()
                .stream()
                .toList();
    }

    public List<DynamoQuery> getImageQueriesForUser(String username) {
        return getQueriesForUser(username, dynamoDBConfiguration.getDynamoDbImageTable());
    }

    public List<DynamoQuery> getTextQueriesForUser(String username) {
        return getQueriesForUser(username, dynamoDBConfiguration.getDynamoDbTextTable());
    }

    public DynamoImageQuery getImageQueryForUser(String username, String queryId) {
        DynamoDbTable<DynamoImageQuery> table = dynamoDbEnhancedClient.table(
                dynamoDBConfiguration.getDynamoDbImageTable(),
                TableSchema.fromBean(DynamoImageQuery.class)
        );

        return table.getItem(r -> r
                .key(k -> k.partitionValue(username).sortValue(queryId)));
    }

    public DynamoTextQuery getTextQueryForUser(String username, String queryId) {
        DynamoDbTable<DynamoTextQuery> table = dynamoDbEnhancedClient.table(
                dynamoDBConfiguration.getDynamoDbTextTable(),
                TableSchema.fromBean(DynamoTextQuery.class)
        );

        return table.getItem(r -> r
                .key(k -> k.partitionValue(username).sortValue(queryId)));
    }

}
