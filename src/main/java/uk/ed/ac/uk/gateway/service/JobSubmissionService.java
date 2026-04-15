package uk.ed.ac.uk.gateway.service;

import com.rabbitmq.client.Connection;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import uk.ed.ac.uk.gateway.configuration.DynamoDBConfiguration;
import uk.ed.ac.uk.gateway.configuration.RabbitMQConfiguration;
import uk.ed.ac.uk.gateway.dto.JobBody;
import uk.ed.ac.uk.gateway.dto.JobRabbitMQMessage;
import uk.ed.ac.uk.gateway.dto.SubmissionTicket;
import uk.ed.ac.uk.gateway.entity.DynamoImageQuery;
import uk.ed.ac.uk.gateway.entity.DynamoQuery;
import uk.ed.ac.uk.gateway.entity.DynamoTextQuery;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobSubmissionService {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfiguration rabbitMQConfiguration;
    private final DynamoDBConfiguration dynamoDBConfiguration;
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final QueryService queryService;

    public SubmissionTicket submitImageTask(JobBody jobBody) {
        // generate unique jobID
        String jobID = java.util.UUID.randomUUID().toString();

        // timestamp
        String timestamp = java.time.Instant.now().toString();

        // job name
        String jobName;
        if(jobBody.jobName() == null || jobBody.jobName().isEmpty()) {
            jobName = "Image Request at "  + timestamp;
        }
        else {
            jobName = jobBody.jobName();
        }

        // create entry in dynamo
        SubmissionTicket submissionTicket = new SubmissionTicket(
                jobID,
                jobName,
                timestamp,
                jobBody.username()
        );

        submitImageJobToDynamo(submissionTicket, jobBody.payload());

        // send message to rabbitmq
        JobRabbitMQMessage rabbitMQMessage = submissionTicket.asRabbitMQMessage(jobBody.payload());
        rabbitTemplate.convertAndSend(
                rabbitMQConfiguration.getRabbitMQExchange(),
                rabbitMQConfiguration.getRabbitMQQueueImage(),
                rabbitMQMessage
        );

        return submissionTicket;
    }

    public SubmissionTicket submitTextTask(JobBody jobBody) {
        // generate unique jobID
        String jobID = java.util.UUID.randomUUID().toString();

        // timestamp
        String timestamp = java.time.Instant.now().toString();

        // job name
        String jobName;
        if(jobBody.jobName() == null || jobBody.jobName().isEmpty()) {
            jobName = resolveJobNameForText(jobBody.payload());
        }
        else {
            jobName = jobBody.jobName();
        }

        // create entry in dynamo
        SubmissionTicket submissionTicket = new SubmissionTicket(
                jobID,
                jobName,
                timestamp,
                jobBody.username()
        );

        submitTextJobToDynamo(submissionTicket, jobBody.payload());

        // send message to rabbitmq
        JobRabbitMQMessage rabbitMQMessage = submissionTicket.asRabbitMQMessage(jobBody.payload());
        rabbitTemplate.convertAndSend(
                rabbitMQConfiguration.getRabbitMQExchange(),
                rabbitMQConfiguration.getRabbitMQQueueText(),
                rabbitMQMessage
        );

        return submissionTicket;
    }

    private String resolveJobNameForText(String text) {
        // use the first 4 words as job name
        List<String> words = Arrays.asList(text.split(" "));
        if(words.size() > 4) {
            // combine first 4 words and elipses
            return String.join(" ", words.subList(0, 4)) + "...";
        }
        else {
            return text;
        }
    }

    private void submitImageJobToDynamo(SubmissionTicket submissionTicket, String imageBase64) {
        DynamoDbTable<DynamoImageQuery> table = dynamoDbEnhancedClient.table(
                dynamoDBConfiguration.getDynamoDbImageTable(),
                TableSchema.fromBean(DynamoImageQuery.class)
        );

        DynamoImageQuery job = submissionTicket.asImageJobSubmission(imageBase64);

        // put job
        table.putItem(PutItemEnhancedRequest.builder(DynamoImageQuery.class)
                .item(job)
                .conditionExpression(Expression.builder()
                        .expression("attribute_not_exists(username) AND attribute_not_exists(queryID)")
                        .build())
                .build());
    }

    private void submitTextJobToDynamo(SubmissionTicket submissionTicket, String text) {
        DynamoDbTable<DynamoTextQuery> table = dynamoDbEnhancedClient.table(
                dynamoDBConfiguration.getDynamoDbTextTable(),
                TableSchema.fromBean(DynamoTextQuery.class)
        );

        DynamoTextQuery job = submissionTicket.asTextJobSubmission(text);

        // put job
        table.putItem(PutItemEnhancedRequest.builder(DynamoTextQuery.class)
                .item(job)
                .conditionExpression(Expression.builder()
                        .expression("attribute_not_exists(username) AND attribute_not_exists(queryID)")
                        .build())
                .build());
    }

}
