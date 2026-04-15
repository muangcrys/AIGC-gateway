package uk.ed.ac.uk.gateway.dto;

public record SecurityResponse(
        boolean success,
        String reason,
        String username
) {
    public static SecurityResponse expiredTokenResponse(String username) {
        return new SecurityResponse(false, "Token has expired", username);
    }
    public static SecurityResponse invalidTokenResponse(String username) {
        return new SecurityResponse(false, "Invalid token", username);
    }

    public static SecurityResponse successResponse(String username) {
        return new SecurityResponse(true, "Success", username);
    }

    public static SecurityResponse unauthorizedResponse(String username) {
        return new SecurityResponse(false, "Unauthorized username", username);
    }
}
