package uk.ed.ac.uk.gateway.entity;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Setter
@DynamoDbBean
public class DynamoImageQuery extends DynamoQuery {
    private String imageBase64;

    @DynamoDbAttribute("imageBase64")
    public String getImageBase64() {
        return imageBase64;
    }

}
