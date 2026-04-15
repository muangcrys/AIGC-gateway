package uk.ed.ac.uk.gateway.entity;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Setter
@DynamoDbBean
public class DynamoQuery {

    private String queryID;
    private String username;
    private String queryName;
    private String timestamp;
    private Float artificialProbability;
    private Boolean finished;
    private String reason;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("username")
    public String getUsername() {
        return username;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("queryID")
    public String getQueryID() {
        return queryID;
    }

    @DynamoDbAttribute("queryName")
    public String getQueryName() {
        return queryName;
    }

    @DynamoDbAttribute("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @DynamoDbAttribute("artificialProbability")
    public Float getArtificialProbability() {
        return artificialProbability;
    }

    @DynamoDbAttribute("finished")
    public Boolean getFinished() {
        return finished;
    }

    @DynamoDbAttribute("reason")
    public String getReason() {
        return reason;
    }

}
