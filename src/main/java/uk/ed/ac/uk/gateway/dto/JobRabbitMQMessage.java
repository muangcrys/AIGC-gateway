package uk.ed.ac.uk.gateway.dto;

public record JobRabbitMQMessage (
        String request_id,
        String username,
        String payload
){
}
