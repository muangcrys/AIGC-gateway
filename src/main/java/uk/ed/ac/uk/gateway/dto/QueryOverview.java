package uk.ed.ac.uk.gateway.dto;

public record QueryOverview(
        String queryID,
        String username,
        String queryName,
        String timestamp,
        String artificialProbability,
        Boolean finished,
        String reason
) {}