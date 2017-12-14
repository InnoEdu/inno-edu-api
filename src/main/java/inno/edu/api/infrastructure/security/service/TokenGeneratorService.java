package inno.edu.api.infrastructure.security.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

import static inno.edu.api.infrastructure.security.jwt.SecurityConstants.EXPIRATION_TIME;
import static inno.edu.api.infrastructure.security.jwt.SecurityConstants.SECRET;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class TokenGeneratorService {
    public String generate(String username) {
        return Jwts.builder()
                .claim("roles", "ROLE_ADMIN")
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(HS256, SECRET.getBytes())
                .compact();
    }
}
