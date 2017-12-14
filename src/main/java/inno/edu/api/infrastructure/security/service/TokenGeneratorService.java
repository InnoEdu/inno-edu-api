package inno.edu.api.infrastructure.security.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static inno.edu.api.infrastructure.security.jwt.SecurityConstants.EXPIRATION_TIME;
import static inno.edu.api.infrastructure.security.jwt.SecurityConstants.SCOPE_ACCESS;
import static inno.edu.api.infrastructure.security.jwt.SecurityConstants.SECRET;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.lang.String.join;

@Service
public class TokenGeneratorService {
    public String generate(String username, List<String> roles) {
        return Jwts.builder()
                .claim(SCOPE_ACCESS, join(",", roles))
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(HS256, SECRET.getBytes())
                .compact();
    }
}
