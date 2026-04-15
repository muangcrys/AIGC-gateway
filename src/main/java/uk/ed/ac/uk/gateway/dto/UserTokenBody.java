package uk.ed.ac.uk.gateway.dto;

public record UserTokenBody (
        String username,
        String token
){
}
