package spring_security_model.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expirationInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret}") String encodedSecret, @Value("${jwt.expiration}") long expirationInMilliseconds) {
        byte[] keyBytes = Decoders.BASE64.decode(encodedSecret);

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        this.expirationInMilliseconds = expirationInMilliseconds;
    }

    /**
     * Generates a JWT after username/password authentication succeeds.
     */
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date issuedAt = new Date();

        Date expirationDate = new Date(issuedAt.getTime() + expirationInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extracts the username stored in the JWT subject.
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts token expiration date.
     */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Verifies token structure, signature and expiration.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            extractAllClaims(token);
            return true;

        } catch (ExpiredJwtException exception) {
            return false;

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public long getExpirationInMilliseconds() {
        return expirationInMilliseconds;
    }

    public long getExpirationInSeconds() {
        return expirationInMilliseconds / 1000;
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
