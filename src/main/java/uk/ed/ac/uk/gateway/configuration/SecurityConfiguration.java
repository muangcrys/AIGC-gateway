package uk.ed.ac.uk.gateway.configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfiguration {
    private final String secret;
    private final SecretKey secretKey;
    private final JwtParser parser;

    public SecurityConfiguration(
            @Value("${SECRET:this-is-a-secret-key-with-at-least-32-bytes}") String secret,
            @Value("${TOKEN_TTL:3600}") Integer tokenTTLSeconds) {
        this.secret = secret;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    @Bean
    public SecretKey getSecretKey() {
        return secretKey;
    }

    @Bean
    public JwtParser getParser() {
        return parser;
    }
}
