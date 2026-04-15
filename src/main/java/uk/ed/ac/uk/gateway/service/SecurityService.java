package uk.ed.ac.uk.gateway.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ed.ac.uk.gateway.configuration.SecurityConfiguration;
import uk.ed.ac.uk.gateway.dto.JobBody;
import uk.ed.ac.uk.gateway.dto.SecurityResponse;
import uk.ed.ac.uk.gateway.dto.UserTokenBody;
import uk.ed.ac.uk.gateway.dto.UserTokenQueryBody;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private final SecurityConfiguration securityConfiguration;

    public SecurityResponse authenticateToken(String username, String token) {
        logger.info("Authenticating user {}", username);
        try {
            Claims claim = securityConfiguration.getParser().parseSignedClaims(token).getPayload();
            String tokenUsername = claim.getSubject();

            if(!username.equals(tokenUsername)) {
                logger.warn("Invalid username in token");
                return SecurityResponse.unauthorizedResponse(username);
            }

            if (claim.getExpiration().before(new java.util.Date())) {
                logger.warn("Token expired");
                return SecurityResponse.expiredTokenResponse(username);
            }

            return SecurityResponse.successResponse(username);
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return SecurityResponse.invalidTokenResponse(username);
        }
    }

    public SecurityResponse authenticateToken(UserTokenBody userTokenBody) {
        return authenticateToken(userTokenBody.username(), userTokenBody.token());
    }

    public SecurityResponse authenticateToken(UserTokenQueryBody userTokenQueryBody) {
        return  authenticateToken(userTokenQueryBody.username(), userTokenQueryBody.token());
    }

    public SecurityResponse authenticateToken(JobBody jobBody) {
        return authenticateToken(jobBody.username(), jobBody.token());
    }
}
