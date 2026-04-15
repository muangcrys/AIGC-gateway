package uk.ed.ac.uk.gateway.dto;

import uk.ed.ac.uk.gateway.entity.DynamoImageQuery;
import uk.ed.ac.uk.gateway.entity.DynamoQuery;
import uk.ed.ac.uk.gateway.entity.DynamoTextQuery;

public record SubmissionTicket (
        String queryId,
        String queryName,
        String timestamp,
        String username
){
    public DynamoTextQuery asTextJobSubmission(String text) {
        DynamoTextQuery dq = new DynamoTextQuery();
        dq.setQueryID(this.queryId);
        dq.setQueryName(this.queryName);
        dq.setTimestamp(this.timestamp);
        dq.setUsername(this.username);
        dq.setFinished(false);
        dq.setText(text);
        dq.setReason("Job submitted. Waiting for inference worker.");
        return dq;
    }

    public DynamoImageQuery asImageJobSubmission(String imageBase64) {
        DynamoImageQuery dq = new DynamoImageQuery();
        dq.setQueryID(this.queryId);
        dq.setQueryName(this.queryName);
        dq.setTimestamp(this.timestamp);
        dq.setUsername(this.username);
        dq.setFinished(false);
        dq.setImageBase64(imageBase64);
        dq.setReason("Job submitted. Waiting for inference worker.");
        return dq;
    }

    public JobRabbitMQMessage asRabbitMQMessage(String payload) {
        return new JobRabbitMQMessage(
                this.queryId,
                this.username,
                payload
        );
    }
}
