package br.com.rodpk.productapi.modules.jwt.service;

import javax.security.auth.message.AuthException;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.rodpk.productapi.modules.jwt.dto.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;



@Service
public class JwtService {
    
    private static final String BEARER = "bearer ";
    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization(String token) throws AuthException {
        try {
            var accesToken = extractToken(token);
            var claims = Jwts
            .parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
            .build()
            .parseClaimsJws(accesToken).getBody();

            var user = JwtResponse.getUser(claims);

            if (user == null || user.getId() == null) {
                throw new AuthException("invalid user");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new AuthException("error while trying to process token");

        }
    }

    private String extractToken(String token) throws AuthException {
        if (token.isEmpty()) throw new AuthException("access token was not informed.");
    

        if (token.toLowerCase().contains(BEARER)) {
            token = token.toLowerCase();
            token = token.replace(BEARER, Strings.EMPTY);
        }

        return token;
    }

}
