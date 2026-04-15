package uk.ed.ac.uk.gateway.dto;

public record JobBody (
        String username,
        String token,
        String jobName,
        String payload
){

}
