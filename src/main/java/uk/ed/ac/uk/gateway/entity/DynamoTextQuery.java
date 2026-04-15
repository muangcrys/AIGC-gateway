package uk.ed.ac.uk.gateway.entity;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Setter
@DynamoDbBean
public class DynamoTextQuery extends DynamoQuery {
    private String text;

    @DynamoDbAttribute("text")
    public String getText() {
        return text;
    }

}
