package uk.ed.ac.uk.gateway.dto;

public record UserTokenQueryBody (
        String username,
        String token,
        String queryID
){
}
